package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.Category;
import bg.softuni.cooking.model.entity.CategoryEnum;
import bg.softuni.cooking.model.entity.Recipe;
import bg.softuni.cooking.model.service.RecipeServiceModel;
import bg.softuni.cooking.model.view.RecipeDetailViewModel;
import bg.softuni.cooking.model.view.RecipeViewModel;
import bg.softuni.cooking.repository.RecipeRepository;
import bg.softuni.cooking.service.CategoryService;
import bg.softuni.cooking.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public RecipeServiceImpl(
      RecipeRepository recipeRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;

    }

    @Override
    public void addRecipe(RecipeServiceModel recipeServiceModel) {
        Recipe recipe = modelMapper.map(recipeServiceModel, Recipe.class);

        String categoryName = recipeServiceModel.getCategory();

        Category category = categoryService.findByCategoryName(CategoryEnum.valueOf(categoryName));
        recipe.setCategory(category);

        recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeViewModel> findRecipesByCategoryEnum(CategoryEnum categoryEnum) {
        return recipeRepository.findByCategory_CategoryName(categoryEnum)
                .stream().map(recipe -> modelMapper.map(recipe, RecipeViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public RecipeDetailViewModel findRecipeById(Long id) {
        return modelMapper.map(recipeRepository.findById(id).orElse(null), RecipeDetailViewModel.class);
    }

    @Override
    public List<RecipeViewModel> findAllRecipes() {
        return recipeRepository.findAllRecipes().stream().map(recipe -> modelMapper.map(recipe, RecipeViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public RecipeServiceModel findById(Long id) {
        return recipeRepository.findById(id).map(recipe -> modelMapper.map(recipe, RecipeServiceModel.class)).orElse(null);
    }

    @Override
    public void seedRecipes() {
        if (recipeRepository.count() == 0) {
            Recipe pizza1 = new Recipe();
            pizza1.setName("PIZZA CAPRICCOSIA");
            pizza1.setIngredients("1 ball Pizza Dough, ¼ cup canned tomatoes, ½ cup mozzarella, 2 ounces very thinly sliced prosciutto cotto or freshly sliced uncured boiled ham, torn into large shreds, ½ cup mushrooms ,3 ounces drained marinated artichokes, sliced into thin wedges");
            pizza1.setPreparationMethod("Top pizza with tomato sauce, spreading outward in a widening circle with the back of a soupspoon. Then sprinkle on mozzarella, prosciutto, mushrooms and artichokes. Bake pizza in preheated oven at 150 degrees C.");
            pizza1.setImageUrl("https://zvezdev.com/attachments/steps_thumbs/1420/IMG-0370.jpg");
            pizza1.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("PIZZA")));
            recipeRepository.save(pizza1);

            Recipe pizza2 = new Recipe();
            pizza2.setName("PIZZA MARGHERITTA");
            pizza2.setIngredients("1 ball Pizza Dough, ½ cup Pizza Sauce, 8 ounces fresh mozzarella, ½ cup sliced cherry tomatoes, 10 basil leaves, 1 sliced red pepper, Extra-virgin olive oil");
            pizza2.setPreparationMethod("Preheat the oven to 200 degrees C. Spread the pizza sauce onto the dough. Top with the fresh mozzarella and tomatoes and bake 10 to 12 minutes, or until the crust is browned. Remove from the oven and top with fresh basil leaves and a pinch of red pepper flakes. Drizzle with olive oil and serve.");
            pizza2.setImageUrl("https://gingerbg.com/image/cache/catalog/yastia/pizza/pizza-margarita-600x400.jpg");
            pizza2.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("PIZZA")));
            recipeRepository.save(pizza2);

            Recipe pizza3 = new Recipe();
            pizza3.setName("PIZZA CALZONE");
            pizza3.setIngredients("800g pizza dough, ½ cup pizza sauce, ½ cup yellow onion diced, ½ cup green bell pepper diced, ½ cup sliced pepperoni, 1 cup mozzarella cheese shredded, 1 tablespoon olive oil");
            pizza3.setPreparationMethod("Preheat oven to 180 degrees C. Divide pizza dough into 4 equal parts and roll them. On half of each dough add sauce, onion, green pepper and sliced pepperoni. Sprinkle the toppings with shredded cheese. Then fold the other half of the dough over the toppings and crimp the edges. Brush with olive oil and bake for about 15 minutes.");
            pizza3.setImageUrl("https://klaspile.com/wp-content/uploads/2016/03/%D0%9A%D0%90%D0%9B%D0%A6%D0%9E%D0%9D%D0%95-600x384.jpg");
            pizza3.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("PIZZA")));
            recipeRepository.save(pizza3);

            Recipe dessert1 = new Recipe();
            dessert1.setName("TIRAMISU");
            dessert1.setIngredients("225ml heavy whipping cream, 225g mascarpone cheese at room temperature, 1/3 cup sugar, 1 tsp vanilla, 1 tbsp Amaretto liquor, 2 cups espresso, 1 pack Ladyfingers, Cocoa powder for dusting the top");
            dessert1.setPreparationMethod("Mix whipping cream, sugar and vanilla. Add in mascarpone cheese and amaretto and continue to whip to stiff peaks. Dip ladyfingers in coffee (and brandy if using) and place in a pan to make the first layer – you can fit about 7 cookies in each layer. Don't let them soak as the will fall apart, just a quick dunk in the coffee is enough. Spread half of the whipped cream mixture on top of the first layer of ladyfingers. Repeat process with the second layer of ladyfingers and cream mixture. Dust the top of dessert with cocoa powder and refrigerate for about 2-4 hours.");
            dessert1.setImageUrl("https://www.az-jenata.bg/media/az-jenata/files/articles/640x480/a060faf12c2cc8d9cfbef9970faa867b.jpg");
            dessert1.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("DESSERT")));
            recipeRepository.save(dessert1);

            Recipe dessert2 = new Recipe();
            dessert2.setName("CREME BRULEE");
            dessert2.setIngredients("6 egg yolks, 6 tablespoons white sugar, 1 teaspoon vanilla extract, 3 cups heavy cream, 2 tablespoons brown sugar");
            dessert2.setPreparationMethod("Preheat oven to 150 degrees C. Beat egg yolks, 4 tbsp white sugar and vanilla in a bowl until thick and creamy. Pour cream into a saucepan and stir over low heat until it boils. Remove the cream from heat. Stir cream into the egg yolk mixture and beat until combined. Pour cream mixture into the top pan of a double boiler. Stir over simmering water for 3 minutes. Remove mixture from heat immediately and pour into a heat-proof dish. Bake in preheated oven for 30 minutes. Remove from oven and cool to room temperature. Refrigerate for at least 1 hour or overnight.\n" +
                    "Preheat oven to broil. In a small bowl combine the remaining 2 tbsp white sugar and brown sugar. Sift this mixture evenly over custard. Place dish under broiler for 2 minutes. Remove from heat and allow to cool. Refrigerate until custard is set again.");
            dessert2.setImageUrl("https://lakomnik.bg/f/news/8/660_f60b6c620242d6cefeed9e8e0ae37808.jpg");
            dessert2.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("DESSERT")));
            recipeRepository.save(dessert2);

            Recipe mainDish1 = new Recipe();
            mainDish1.setName("MOUSSAKA");
            mainDish1.setIngredients("1kg potatoes, cut in small cubes, 1/2kg ground meat, 1 chopped onion, 2 cups milk, 4 eggs, 1/2 cup oil, 2 tbsp paprika, 1 tbsp salt, 1 tsp crushed black pepper");
            mainDish1.setPreparationMethod("Start with cooking the onion in a pan with 1/4 oil until golden brown. Then add the meat, the pepper, the paprika, and half the salt. Fry until meat gets brown and then remove the pan from the heat. Mix well with the potatoes and the other 1/2 tbsp of salt. Add the mixture in a casserole pan with the rest of the oil. Bake in oven for about 40 minutes on 220 C. In the meantime mix the milk and the eggs separately and pour on top  of the meal for the last 10 minutes in the oven until it turns brownish.");
            mainDish1.setImageUrl("https://www.bonapeti.bg/uploads/recipes/rec16463/recipe_image0_540x405_147738720631.jpg");
            mainDish1.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("MAIN_DISH")));
            recipeRepository.save(mainDish1);

            Recipe mainDish2 = new Recipe();
            mainDish2.setName("STUFFED PEPPERS");
            mainDish2.setIngredients("600g cut meat, 4 medium red peppers, 1/2 cup minced onion, 2 tsp minced garlic, 1 can diced tomatoes, 1/2 cup cooked white or brown rice, 3 tbsp tomato paste, 2 tsp parsley, 1/2 tsp salt, 1/4 tsp black pepper, hopped fresh parsley");
            mainDish2.setPreparationMethod("Cut tops of bell peppers and set the tops aside. Remove the seeds from the peppers. Arrange peppers in baking dish and place the tops on empty peppers. Cover them with aluminum foil and bake for 15 minutes. Meanwhile heat large nonstick skillet over medium heat. Add cut meat, onion and garlic and cook 3 to 4 minutes. Stir in tomatoes, rice, tomato paste, dried parsley, salt and black pepper and cook for 5 minutes. Remove pepper tops. Divide the mixture evenly among peppers and replace the tops. Bake in 200°C oven 25 minutes. Garnish with parsley.");
            mainDish2.setImageUrl("https://s.rozali.com/p/p/y/pylneni-chushki-s-kinoa-429675-500x334.jpg");
            mainDish2.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("MAIN_DISH")));
            recipeRepository.save(mainDish2);

            Recipe mainDish3 = new Recipe();
            mainDish3.setName("SPAGHETTI CARBONARA");
            mainDish3.setIngredients("3 egg yolks, 40g Parmesan cheese and some extra to serve, 150g piece of higher-welfare pancetta, 200g dried spaghetti, 1 clove of garlic, extra virgin olive oil");
            mainDish3.setPreparationMethod("Put the egg yolks in a bowl, grate in the Parmesan, season with pepper, then mix well with a fork and put to one side. Cut any hard skin of pancetta, then chop the meat. Cook spaghetti in a large pan of boiling salted water. Meanwhile, rub the pancetta skin, if any, all over the base of a medium frying pan. Peel the garlic, add it to the pan and leave it for 1 minute. Stir in the pancetta, then cook for 5 minutes. Pick out and discard the garlic from the pan, then reserving some of the cooking water, drain and add the spaghetti.  Add a splash of the cooking water and toss well, season with pepper, pour in the egg mixture. Toss well, adding more cooking water until it’s lovely and glossy. Serve with a grating of Parmesan and an extra twist of pepper.");
            mainDish3.setImageUrl("https://www.bonapeti.bg/uploads/recipes/rec16397/recipe_image0_540x405_147629589082.jpg");
            mainDish3.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("MAIN_DISH")));
            recipeRepository.save(mainDish3);

            Recipe salad1 = new Recipe();
            salad1.setName("RUSSIAN VINEGRETTE");
            salad1.setIngredients("3 medium beets, 3 medium potatoes, 3 medium carrots, 1/2 cup sauerkraut, 3 medium pickles, 2 tbsp sunflower or olive oil, 1 tbsp white vinegar, 1 small finely chopped onion");
            salad1.setPreparationMethod("Boil beets for 1 hour and in a separate pot, boil potatoes and carrots about 30 minutes. Drain the vegetables and let them get cool. Peel the potatoes, beets and carrots, than dice pickles, beets, potatoes, carrots and onion. Mix beets with 1 tbsp of Sunflower Oil. Mix together beets and the rest of ingredients with the 2nd tbsp of Sunflower or Olive oil and 1 Tbsp of vinegar. Refrigerate until it gets ready to use.");
            salad1.setImageUrl("https://www.supichka.com/files/images/2956/fit_1400_933.jpg");
            salad1.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("SALAD")));
            recipeRepository.save(salad1);

            Recipe salad2 = new Recipe();
            salad2.setName("GREEK SALAD");
            salad2.setIngredients("1 unpeeled cucumber, 1 large-diced red pepper, 1 large-diced yellow bell pepper, 2 tomatoes, 1/2 red onion, 100g feta cheese, 1/2 cup calamata olives\n" +
                    "For the vinaigrette: 2 cloves garlic, 1 tsp oregano, 1/2 tsp Dijon mustard, 1/4 cup red wine vinegar, 1 tsp salt, 1/2 tsp black pepper, 1/2 cup olive oil");
            salad2.setPreparationMethod("Place the cucumber, peppers, tomatoes and red onion in a large bowl. For the vinaigrette mix together the garlic, oregano, mustard, vinegar, salt and pepper in a small bowl. Add the olive oil. Pour vinaigrette over the vegetables. Add the feta and olives. Set aside for 30 minutes.");
            salad2.setImageUrl("https://www.kulinarno-joana.com/wp-content/uploads/2009/08/p1150898.jpg");
            salad2.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("SALAD")));
            recipeRepository.save(salad2);

            Recipe salad3 = new Recipe();
            salad3.setName("OLIVIE SALAD");
            salad3.setIngredients("150g cubed meat, 3 cooked potatoes, 3 cooked carrots, 6 cooked eggs, 3 dill pickles, 1 sweet onion,1 cup frozen peas, 1/2 sour cucumber, 1 cup mayo, dill, salt and pepper to taste");
            salad3.setPreparationMethod("Bring potatoes and carrots to boil and cook about 20-25 minutes. Cook eggs 8-10 minutes. Cube all of the ingredients and mix them. Add mayo, salt and pepper to taste. Add fresh dill and mix.");
            salad3.setImageUrl("https://www.1001recepti.com/images/photos/articles/ruska_salata_olivie_2_[60].jpg");
            salad3.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("SALAD")));
            recipeRepository.save(salad3);

            Recipe sushi1 = new Recipe();
            sushi1.setName("CALIFORNIA SUSHI ROLLS");
            sushi1.setIngredients("2 cups rinsed and drained sushi rice\n" +
                    "2 cups water\n" +
                    "1/4 cup rice vinegar\n" +
                    "2 tablespoons sugar\n" +
                    "1/2 teaspoon salt\n" +
                    "2 tablespoons sesame seeds, toasted\n" +
                    "2 tablespoons black sesame seeds");
            sushi1.setPreparationMethod("1. In a large saucepan, combine rice and water and leave for 30 minutes. Bring to a boil. Reduce heat to low, cover and simmer for 15-20 minutes. Remove from the heat. Let stand, covered, for 10 minutes.\n" +
                    "2. Meanwhile, in small bowl, combine the vinegar, sugar and salt, stirring until sugar is dissolved.\n" +
                    "3. Transfer rice to a large shallow bowl, drizzle with vinegar mixture. With a wooden paddle or spoon, stir rice with a slicing motion to cool slightly. Cover with a damp cloth to keep moist.\n" +
                    "4. Sprinkle toasted and black sesame seeds onto a plate, set aside. Place sushi mat on a work surface, line with plastic wrap. Place 3/4 cup rice on plastic. With moistened fingers, press rice into an 8-in. square. Top with one nori sheet.\n" +
                    "5. Arrange a small amount of cucumber, crab and avocado about 1-1/2 in. from bottom edge of nori sheet. Roll up rice mixture over filling, using the bamboo mat to lift and compress the mixture while rolling; remove plastic wrap as you roll.\n" +
                    "6. Remove mat, roll sushi rolls in sesame seeds. Cover with plastic wrap. Repeat with remaining ingredients to make eight rolls. Cut each into eight pieces. Serve with soy sauce, wasabi and ginger slices if desired.");
            sushi1.setImageUrl("https://static.framar.bg/thumbs/6/recepies/sushi.jpg");
            sushi1.setCategory(categoryService.findByCategoryName(CategoryEnum.valueOf("SUSHI")));
            recipeRepository.save(sushi1);


        }

    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }


}
