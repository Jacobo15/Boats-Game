

var url_string = window.location.href
var url = new URL(url_string);
var gpId = url.searchParams.get("gp");
console.log(gpId);

var app = new Vue ({
    el: "#vue-app",
    data: {
        game: [],
        tableX:[1,2,3,4,5,6,7,8,9,10],
        tableY:["A","B","C","D","E","F","G","H","I","J","K"],
        cruiser: [],
        destroyer: [],
        battlesShip: [],
        salvo: [],
        salvoRival: [],
        ourData: [],
        ourDataSalvos:{
            locations: [],
             },
        shipName: {},
        check: true,
        checkS: true,
    },

   methods: {
positionsBoats: function () {
                      var positionsCruiser = [];
                      var positionsDestroyer = [];
                      var positionsBattlesShip = [];
                      console.log(this.game.ships.length)
                      for(var i=0; i < this.game.ships.length; i++){
                            for(var j=0; j< this.game.ships[i].locations.length; j++){
                        if(this.game.ships[i].shipName == "cruiser"){
                        positionsCruiser.push(this.game.ships[i].locations[j])
                        }
                        else if(this.game.ships[i].shipName == "destroyer"){
                        positionsDestroyer.push(this.game.ships[i].locations[j])
                        }
                        else{
                        positionsBattlesShip.push(this.game.ships[i].locations[j])
                        }
                        }
                        }
                        console.log(positionsCruiser)
                        console.log(positionsDestroyer)
                        console.log(positionsBattlesShip)
                        this.cruiser = positionsCruiser;
                        this.destroyer = positionsDestroyer;
                        this.battlesShip = positionsBattlesShip;
                        console.log(this.destroyer)
                        if(this.game.ships.length == 3){
                                            document.getElementById("cruiser").style.display = "none";
                                            document.getElementById("cruiserCheck").style.display = "none";
                                            document.getElementById("destroyer").style.display = "none";
                                            document.getElementById("destroyerCheck").style.display = "none";
                                            document.getElementById("battleship").style.display = "none";
                                            document.getElementById("battleshipCheck").style.display = "none";
                                            document.getElementById("sendShips").style.display = "none";
                                            }
                        if(document.getElementById("sendShips").style.display == ""){
                        document.getElementById("sendSalvos").style.display = "none"
                        }
                        this.printBoats();
                  },

                  printBoats: function(){
                  var positions = document.querySelectorAll("td");
                  for(i=0; i < positions.length-110; i++ ){
                    for(j=0; j < this.cruiser.length; j++){
                    if(this.cruiser[j] == positions[i].innerHTML ){
                        positions[i].classList.remove("water")
                        positions[i].classList.add("cruiser")
                     }
                  }
                    for(k=0; k < this.destroyer.length; k++){
                      if(this.destroyer[k] == positions[i].innerHTML ){
                      positions[i].classList.remove("water")
                        positions[i].classList.add("destroyer")
                      }
                  }
                  for(m=0; m < this.battlesShip.length; m++){
                     if(this.battlesShip[m] == positions[i].innerHTML ){
                      positions[i].classList.remove("water")
                      positions[i].classList.add("battleship")
                }
              }
                  }
                  },

                 positionsSalvo: function(){
                    var positionsSal = [];
                    var salRival = [];
                    for (i=0; i < this.game.salvoes.length; i++){
                       for(j=0; j < this.game.salvoes[i].info.length; j++ ){
                            for (k=0; k < this.game.salvoes[i].info[j].locations.length; k++){
                    if(this.game.salvoes[i].gamePlayerId == gpId ){
                    positionsSal.push(this.game.salvoes[i].info[j].locations[k])
                        }
                    else{
                    salRival.push(this.game.salvoes[i].info[j].locations[k])
                    }
                   }
                            }
                            }
                    this.salvo = positionsSal;
                    this.salvoRival = salRival;
                    this.printSalvo();
                    this.printSalvoRival();

                    },

                 printSalvo: function(){
                 var positions = document.querySelectorAll("td");
                 for(i=110; i < positions.length; i++ ){
                  for(j=0; j < this.salvo.length; j++){
                    if(this.salvo[j] == positions[i].innerHTML ){
                       positions[i].classList.remove("normal")
                       positions[i].classList.add("salvo")

                         }
                     }
                     }
                    this.printHits();

},

                    printHits: function(){
                    if( this.game.hits.length != undefined){
                    var positions = document.querySelectorAll("td");
                      for(i=110; i < positions.length; i++){
                      for(j=0; j < this.game.hits.length; j++){
                       if(this.game.hits[j] == positions[i].innerHTML ){
                         positions[i].classList.remove("salvo")
                         positions[i].classList.add("hits")
                                             }
                                         }
                                         }
                                         }
                    },
                        printSalvoRival: function(){
                        var positions = document.querySelectorAll("td");
                        for(i=0; i < positions.length-110; i++ ){
                            for(j=0; j < this.salvoRival.length; j++){
                            if(this.salvoRival[j] == positions[i].innerHTML && positions[i].classList.contains("cruiser")){
                            positions[i].classList.add("salvoIn")}
                            else if(this.salvoRival[j] == positions[i].innerHTML && positions[i].classList.contains("destroyer")){
                            positions[i].classList.add("salvoIn")
                            }
                            else if(this.salvoRival[j] == positions[i].innerHTML && positions[i].classList.contains("battleship")){
                            positions[i].classList.add("salvoIn")
                            }
                            else if(this.salvoRival[j] == positions[i].innerHTML && positions[i].classList.contains("water")){
                             positions[i].classList.add("salvoOut")
                            }
}}
},

vertical(name){
if( name == "destroyer"){
if (document.getElementById("destroyer").classList == "destroyerDiv" ){
document.getElementById("destroyer").classList.remove("destroyerDiv")
document.getElementById("destroyer").classList.add("destroyerDivVer")
}
else{
document.getElementById("destroyer").classList.remove("destroyerDivVer")
document.getElementById("destroyer").classList.add("destroyerDiv")
}
}
if( name == "cruiser"){
if (document.getElementById("cruiser").classList == "cruiserDiv" ){
document.getElementById("cruiser").classList.remove("cruiserDiv")
document.getElementById("cruiser").classList.add("cruiserDivVer")
}
else{
document.getElementById("cruiser").classList.remove("cruiserDivVer")
document.getElementById("cruiser").classList.add("cruiserDiv")
}
}
if( name == "battleship"){
if (document.getElementById("battleship").classList == "battleShipDiv" ){
document.getElementById("battleship").classList.remove("battleShipDiv")
document.getElementById("battleship").classList.add("battleShipDivVer")
}
else{
document.getElementById("battleship").classList.remove("battleShipDivVer")
document.getElementById("battleship").classList.add("battleShipDiv")
}
}
},

setShipType(object){

app.shipName = object;

console.log(app.shipName);
},

setShip(table,td){
console.log(app.ourData)

if(app.shipName.name !== undefined){
let ship = {}
 ship = {
 shipName: app.shipName.name,
       locations: []
}
console.log(document.getElementById(app.shipName.name).classList)
if(document.getElementById(app.shipName.name).classList == "destroyerDiv" || document.getElementById(app.shipName.name).classList == "cruiserDiv" || document.getElementById(app.shipName.name).classList == "battleShipDiv" ){
if(app.ourData.length != 0){
this.checking(table,td);
}
if( (td + app.shipName.length) <= 11 && app.check == true ){
for( let i=0; i < app.shipName.length; i++){
ship.locations.push(table + (td + i))

}
 app.ourData.push(ship)

if(app.shipName.name == "cruiser"){
            app.cruiser = ship.locations
            }
            if(app.shipName.name == "destroyer"){
            app.destroyer = ship.locations

            }
            if(app.shipName.name == "battleship"){
            app.battlesShip = ship.locations

            }
document.getElementById(app.shipName.name).style.display = "none";
document.getElementById(app.shipName.name + "Check").style.display = "none";

}
else{
alert("incorrect position")
}
}
else{
if(app.ourData.length != 0){
this.checkingVer(table,td);
}
console.log(table.charCodeAt(0) + app.shipName.length)
if( (table.charCodeAt(0) + app.shipName.length) <= 76 && app.check == true ){
for( let i=0; i < app.shipName.length; i++){
console.log(String.fromCharCode(table.charCodeAt(0) + i) + td )
ship.locations.push(String.fromCharCode(table.charCodeAt(0) + i) + td )

if(app.shipName.name == "cruiser"){
            app.cruiser = ship.locations
            }
            if(app.shipName.name == "destroyer"){
            app.destroyer = ship.locations

            }
            if(app.shipName.name == "battleship"){
            app.battlesShip = ship.locations

            }
}
 app.ourData.push(ship)
document.getElementById(app.shipName.name).style.display = "none";
document.getElementById(app.shipName.name + "Check").style.display = "none";
}
else{
alert("incorrect position")
}
}
}
else{
alert("select boat")
}
app.check = true;
app.shipName = {}
this.printBoats();
},
checking(table,td){
for(i=0; i < app.ourData.length; i++){
for(j=0; j <app.ourData[i].locations.length; j++){
for(k=0; k < app.shipName.length; k++){
if(app.ourData[i].locations[j] == (table + (td + k)) ){
app.check = false;
}
}
}
}
},
checkingVer(table,td){
for(i=0; i < app.ourData.length; i++){
for(j=0; j <app.ourData[i].locations.length; j++){
for(k=0; k < app.shipName.length; k++){
if(app.ourData[i].locations[j] == String.fromCharCode(table.charCodeAt(0) + k) + td ){
app.check = false;
}
}
}
}

},
setSalvo(table, td){
this.checkSalvo(table, td);
if(app.checkS == true && !app.salvo.includes(table + td)){
if( app.ourDataSalvos.locations.length < 3){
app.ourDataSalvos.locations.push(table + td);
app.salvo.push(table + td);
this.printSalvo();
}
else{
alert("All salvos are in")
}
}
else{
alert("incorrect position salvos")
}
app.checkS = true;

},

checkSalvo(table,td){
console.log("hola")
for(k=0; k < app.game.salvoes.length; k++){
for(i=0; i < app.game.salvoes[k].info.length; i++ ){
console.log("1")
for(j=0; j < app.game.salvoes[k].info[i].locations.length; j++){
console.log("2")
if(app.game.salvoes[k].info[i].locations[j] == (table + td)&& app.game.salvoes[k].gamePlayers == gpId) {
app.checkS = false
console.log(app.checkS);
}
}
}
}
},


sendShips(){



          fetch("/api/games/players/"+ gpId +"/ships", {
                  credentials: 'include',
                  headers: {
                      'Content-Type': 'application/json'
                  },
                  method: 'POST',
                  body: JSON.stringify(app.ourData)
              })
              .then(function (data) {
                  console.log(data);

                return data.json()

              })
                .then(function (data) {
                    console.log(data)
                    console.log(app.cruiser)
                    console.log(app.destroyer)
                    location.reload();

                  })
              .catch(function (error) {
                  console.log('Request failure: ', error);

              });
      },

sendSalvos(){

console.log(app.ourDataSalvos)
          fetch("/api/games/players/"+ gpId +"/salvoes", {
                  credentials: 'include',
                  headers: {
                      'Content-Type': 'application/json'
                  },
                  method: 'POST',
                  body: JSON.stringify(app.ourDataSalvos)
              })
              .then(function (data) {
                  console.log(data);

                return data.json();

              })
                .then(function (data) {
                    console.log(data)
                location.reload();



                  })
              .catch(function (error) {
                  console.log('Request failure: ', error);

              });
      },

   },

created() {

    fetch("/api/game_view/"+ gpId , {
                        method: "GET",
                    })
                    .then(function (response) {

                        if (response.ok) {
                            return response.json();
                        }else{
                            alert ("No cheatting")
                        }

                    })
                    .then(function (json) {
                    console.log(json)

                    app.game = json;
                    app.ourDataSalvos.turn == app.game.salvoes[0].info.length
                    console.log(app.game.salvoes[0].info.length);
                    console.log(app.game.gamePlayers[0].player.userName)
                     console.log(app.game.salvoes)
                     console.log(app.ourData)
                    if(app.game.status.includes("waiting for opponent") ){
                       document.getElementById("boatsDiv").style.display = "none"
                       document.getElementById("tableDiv").style.display = "none"
                       document.getElementById("tableSalvos").style.display = "none"
                      }
                    if(app.game.status.includes("Place Ships")){
                    document.getElementById("tableSalvos").style.display = "none"
                    }
                    if(app.game.status.includes("Waiting Opponent Ships")){
                    document.getElementById("sendSalvos").style.display = "none"
                    }
                    if(app.game.status.includes("Waiting Opponent Salvos")){
                    document.getElementById("sendSalvos").style.display = "none"
                    }
                    if(app.game.status.includes("fire salvos") && app.game.finish != ""){
                    document.getElementById("boatsDiv").style.display = "none"
                    document.getElementById("tableDiv").style.display = "none"
                    document.getElementById("tableSalvos").style.display = "none"
                    document.getElementById("tableBoats").style.display = "none"
                    document.getElementById("gameStatus").style.display = "none"
                    }





                     app.positionsBoats()
                     app.positionsSalvo()
                    })
                    .catch(function (error) {
                        // called when an error occurs anywhere in the chain
                        console.log("Request failed: " + error.message);
                    });

    },
computed: {


    },

});