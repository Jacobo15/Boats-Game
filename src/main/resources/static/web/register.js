var app = new Vue ({
    el: "#vue-app",
    data: {
        email: "",
        password: "",
        userName: "",
        firstName: "",
        lastName: ""
    },

   methods: {



registerPlayer(){

var ourData = {
              email: this.email,
              password: this.password,
              userName: this.userName,
              firstName: this.firstName,
              lastName: this.lastName
          }
          fetch("/api/players", {
                  credentials: 'include',
                  headers: {
                      'Content-Type': 'application/x-www-form-urlencoded'
                  },
                  method: 'POST',
                 body: this.getBody(ourData)
              })
              .then(function (data) {
              console.log(data);
              if(data.status == 201 ){

                  location.replace("../web/games.html");
                }
              if( data.status == 403 ){
                alert("Missing data to write");
               }
               if( data.status == 409 ){
                alert("Username already exists");
               }


              })

              .catch(function (error) {
                  console.log('Request failure: ', error);

              });
      },
      getBody(json) {
          var body = [];
          for (var key in json) {
              var encKey = encodeURIComponent(key);
              var encVal = encodeURIComponent(json[key]);
              body.push(encKey + "=" + encVal);
          }
          return body.join("&");
      },


},



created() {

},
computed: {


    },

});