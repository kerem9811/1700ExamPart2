'use strict';

/*Task 2
2. Create a JS method with a JS object that will take into account all the fields described in
the first task.Display the information you get into your new object inside a console log or an
alert. (Please make sure to show the proper code for activation of this method inside the
HTML button tag - you can copy the button tag you used in the first task and just add code
on top of it). Also, please make a call towards a Java rest endpoint using Jquery where to
send the object you just filled. ( We don't have the endpoint for now, but let's imagine it's
name is : "/saveCitizen")*/
$(document).ready(() => {
    console.log('The DOM is ready!');

    function createCitizenObject() {
        let phoneValidity = document.getElementById('phone').checkValidity();
        let emailValidity = document.getElementById('email').checkValidity();
        if (phoneValidity && emailValidity) {
            console.log("All fields are valid");
            const newCitizen = {
                firstname: $("#firstname").val,
                lastname: $("#lastname").val,
                birthday: $("#birthday").val,
                ssn: $("#ssn").val,
                phone: $("#phone").val,
                email: $("#email").val,
                city: $("#city").val,
                street: $("#street").val,
            }
            console.log(newCitizen);
            return newCitizen;
        } else {
            alert("Please fill in all fields correctly");
        }
    }

    async function saveCitizen() {
        let citizen = createCitizenObject();
        try {
            $.ajax({
                url: "/saveCitizen", // the backend url
                type: "POST", // Ensure POST method
                data: JSON.stringify(citizen),  // Serialize to JSON
                contentType: "application/json", //specify that it is json
                success(data) {
                    console.log("Success submitting data to backend: " + data);
                },
                error: function (error) {
                    console.log(error);
                    alert('Could not save' + error);
                }
            });
        } catch (error) {
            console.log(error);
            alert('Could not save' + error);
        }
    }

    $('#citizenform').submit(async event => {
        event.preventDefault();
        await saveCitizen();

    })
})

