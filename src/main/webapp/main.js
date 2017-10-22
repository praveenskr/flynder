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
            getUserProfileDetails(response.authResponse.userID, response.authResponse.accessToken);
        } else {
            FB.login();
        }
    });
}


function getUserProfileDetails(user_id, access_token) {
    $.ajax({
        url: "https://graph.facebook.com/"+user_id+"?fields=id,name,gender,location&access_token="+access_token,
        method : "GET",
        processData: false
    }).done(function(responseData) {
        getUserLocationDetails(responseData, access_token);
    });
}

function getUserLocationDetails(profile_response, access_token) {
    $.ajax({
        url: "https://graph.facebook.com/"+profile_response.location.id+"?fields=location&access_token="+access_token,
        method : "GET",
        processData: false
    }).done(function(responseData) {
        saveUserProfile(access_token, responseData, profile_response)
    });
}

function saveUserProfile(access_token, location_response, profile_response) {
    var jsonObj = {
        "userId":  profile_response.id,
        "number": "+91 8943396673",
        "gender": profile_response.gender,
        "locationNumber":location_response.id,
        "latitude": location_response.location.latitude,
        "longitude" : location_response.location.longitude,
        "accessToken":access_token,
        "name": profile_response.name
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
        getUserProfileDetails(response.authResponse.userID, response.authResponse.accessToken);
    });
}