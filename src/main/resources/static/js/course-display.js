const coursesList = document.getElementById('coursesList')

const allCourses=[];

fetch("http://localhost:8080/courses/api")
.then(response => response.json()
    .then(data => {
        for ( let course of data) {
            allCourses.push(course)
        }
        console.log(allCourses);

        displayCourses(allCourses);
    })
)



const displayCourses = (courses) => {
    coursesList.innerHTML = courses.map((c) => {
        return ` <div class="coursesContainer">
                <div class="col-md-12">
                    <div class="courseInfo">
                        <div class="card w-80 h-50 mb-5">
                            <div class="card-body">
                                <h4 class="card-title font-weight-bold text-uppercase text-danger">${c.name}</h4>
                                    <ul>
                                        <li>
                                            <p>Course location: ${c.location}</p>
                                        </li>
                                        <li>
                                            <p>Start date: ${c.startDate}</p>
                                        </li>
                                        <li>
                                            <p>End date: ${c.endDate}</p>
                                        </li>
                                        <li>
                                            <p>Course organiser: ${c.organiserName}</p>
                                        </li>
                                        <li>
                                            <p>Organiser contact: ${c.organiserEmail}</p>
                                        </li>
                                         <li>
                                            <p>Number of participants: ${c.participants}</p>
                                        </li> 
                                    </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`
    }).join('');
}



