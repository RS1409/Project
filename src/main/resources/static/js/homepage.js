function hideComments(id) {
    var x = document.getElementById(id);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function check_pass() {
    if (document.getElementById('password').value ==
            document.getElementById('password2').value) {
        document.getElementById('saveButton').disabled = false;
        document.getElementById('passTitle').style.color = 'green';
        document.getElementById('verifyPassTitle').style.color = 'green';
    } else {
        document.getElementById('saveButton').disabled = true;
        document.getElementById('passTitle').style.color = 'red';
        document.getElementById('verifyPassTitle').style.color = 'red';
    }
}

function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#img')
                    .attr('src', e.target.result)
                    .width(150)
                    .height(150);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }


function scrollDown(){
    var element = document.getElementById('chatScroll');
    element.scrollTop = element.scrollHeight;
}
