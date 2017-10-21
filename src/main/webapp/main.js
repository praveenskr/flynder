window.fbAsyncInit = function() {
    FB.init({
        appId      : '125673294804779',
        cookie     : true,
        xfbml      : true,
        version    : 'v2.10'
    });

    FB.AppEvents.logPageView();

    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
            console.log('Logged in.');
        }
        else {
            FB.login();
        }
    });

};

(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


function checkLoginState() {
    FB.getLoginStatus(function(response) {
        console.log(JSON.stringify(response));
        if (response.status === 'connected') {
            console.log('Logged in.'+response.authResponse.userID);
            var jsonObj = {
              "userId":  response.authResponse.userID
            };
            $.ajax({
                url: "user/add.do",
                data: JSON.stringify(jsonObj),
                method : "POST",
                dataType: "json",
                processData: false,
                contentType: "application/json"
            }).done(function(responseData) {
                console.log(JSON.stringify(responseData));
            });
        } else {
            FB.login();
        }
    });
}

