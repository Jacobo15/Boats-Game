
var app = new Vue ({
    el: "#vue-app",
    data: {
        all: [],
        games: [],
        players: [],
        email: "",
        password: ""

    },

   methods: {
getPlayers: function(){
          var dif = [];
          var scores =[];
          for(i=0; i < this.games.length; i++){
             for(j=0; j < this.games[i].gamePlayers.length; j++){
             if(!dif.includes(this.games[i].gamePlayers[j].player.name)&& this.games[i].gamePlayers[j].score != null){
             dif.push(this.games[i].gamePlayers[j].player.name);
             scores.push({"Name": this.games[i].gamePlayers[j].player.name,
                            "Total": 0,
                            "Win": 0,
                            "Loss": 0,
                            "Tied":0,
                            });
             }
             }
          }
     console.log(dif)

       this.players = scores;
     console.log(this.players)
     app.getScores();
        },

getScores: function(){
    for(i=0; i < this.games.length; i++){
     for(j=0; j < this.games[i].gamePlayers.length; j++){
        for(k=0; k < this.players.length; k++){
         if(this.games[i].gamePlayers[j].player.name == this.players[k].Name && this.games[i].gamePlayers[j].score == 1){
            this.players[k].Win += 1;
            this.players[k].Total += 1;

        }
        else if(this.games[i].gamePlayers[j].player.name == this.players[k].Name && this.games[i].gamePlayers[j].score == 0.5){
                    this.players[k].Tied += 1;
                     this.players[k].Total += 0.5;
            }
        else if(this.games[i].gamePlayers[j].player.name == this.players[k].Name && this.games[i].gamePlayers[j].score == 0){
                            this.players[k].Loss += 1
                             this.players[k].Total += 0;
                    }
                }
        }
}
console.log(this.players)
this.players.sort((a,b) => a.Total < b.Total ? 1 : a.Total > b.Total ? -1 : a.Win + a.Loss + a.Tied < b.Win + b.Loss + b.Tied ? 1: a.Win + a.Loss + a.Tied > b.Win + b.Loss + b.Tied ? -1: 0)
},

logoutPlayer() {
    fetch("/api/logout", {
        credentials: 'include',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'POST'
        //body: this.getBody(ourData)
    })
    .then(function (data) {
        location.reload();
        alert("logged out");
    })
    .catch(function (error) {
        console.log('Request failure: ', error)
    })
},

loginPlayer(){

var ourData = {
              name: this.email,
              password: this.password
          }
          fetch("/api/login", {
                  credentials: 'include',
                  headers: {
                      'Content-Type': 'application/x-www-form-urlencoded'
                  },
                  method: 'POST',
                  body: this.getBody(ourData)
              })
              .then(function (data) {
                  console.log(data);
                  if(data.status == 200){
                    location.reload();
                  }
                   if(data.status == 401){
                     alert("email or password wrong")
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


 myGame(gamePlayers){
  for(j=0; j <gamePlayers.length; j++){
    if(gamePlayers[j].player.id == this.all.player.id){
console.log(gamePlayers[j].id)
    return true;
    }
}
return false;
 },

thatGame(gamePlayers){
     for(j=0; j < gamePlayers.length; j++){
       if(gamePlayers[j].player.id == this.all.player.id){
   console.log(gamePlayers[j].id)
       location.href = "./game.html?gp=" + gamePlayers[j].id
       }
   }
    },


createGame(){

var ourData = {
              name: this.email,
              password: this.password
          }
          fetch("/api/games", {
                  credentials: 'include',
                  headers: {
                      'Content-Type': 'application/x-www-form-urlencoded'
                  },
                  method: 'POST',
                  body: this.getBody(ourData)
              })
              .then(function (data) {
                  console.log(data);
                  if (data.status == 401){
                  alert("No authorized")
                  }
                  else{
                  return data.json()
                  }
              })
                .then(function (data) {
                    console.log(data);

                    return location.href = "./game.html?gp=" + data.gpId

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

joinGame(id){

var ourData = {
              name: this.email,
              password: this.password
          }
          fetch("/api/game/" + id + "/players" , {
                  credentials: 'include',
                  headers: {
                      'Content-Type': 'application/x-www-form-urlencoded'
                  },
                  method: 'POST',
                  body: this.getBody(ourData)
              })
              .then(function (data) {
                  console.log(data);
                  if (data.status == 401){
                   alert("No authorized")
                    }
                   if(data.status == 403){
                   alert ("Game is empty")
                   }
                    else{
                    return data.json()
                     }

              })
              .then(function (data) {
                    console.log(data);

             return location.href = "./game.html?gp=" + data.gpId

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
      }

},




created() {

document.getElementById("logout-form").style.display = "none";

    fetch("/api/games", { method: "GET" })
        .then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.statusText);
            }
        })
        .then(function (json) {
            console.log(json);

             app.all = json;


            if(json.player == null){
                 document.getElementById("login-form").style.display = "";
                 document.getElementById("logout-form").style.display = "none";
                 document.getElementById("register").style.display = "";

            } else{
                        app.games = json.games;
                             app.getPlayers();

                 document.getElementById("login-form").style.display = "none";
                 document.getElementById("logout-form").style.display = "";
                 document.getElementById("register").style.display = "none";
            }


        })
        .catch(function (error) {
            // called when an error occurs anywhere in the chain
            console.log("Request failed: " + error.message);
        })
},
computed: {




    },

});