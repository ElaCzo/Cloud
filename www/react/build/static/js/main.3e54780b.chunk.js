(this["webpackJsonpcloud-fb-react"]=this["webpackJsonpcloud-fb-react"]||[]).push([[0],{15:function(e,t,a){e.exports=a(27)},20:function(e,t,a){},21:function(e,t,a){e.exports=a.p+"static/media/logo.5d5d9eef.svg"},22:function(e,t,a){},27:function(e,t,a){"use strict";a.r(t);var n=a(0),r=a.n(n),o=a(9),i=a.n(o),c=(a(20),a(10)),s=a(11),l=a(13),u=a(12),h=a(14),d=(a(21),a(22),a(1)),f=(a(23),function(e){return""!==e.value?console.log("rien"):e.livresRef.get().then((function(e){e.forEach((function(e){console.log(e.data())}))})),null}),m=function(e){function t(){var e;Object(c.a)(this,t),e=Object(l.a)(this,Object(u.a)(t).call(this));var a={apiKey:"AIzaSyB-OG_3lnqcdChpwD42uKvYWMHGZWlc4Ow",authDomain:"daar-a1ea7.firebaseapp.com",databaseURL:"https://daar-a1ea7.firebaseio.com",projectId:"daar-a1ea7",storageBucket:"daar-a1ea7.appspot.com",messagingSenderId:"653755445186",appId:"1:653755445186:web:be24541935e8161d02b05b",measurementId:"G-BGZWWNYW0E"};try{d.initializeApp(a),d.analytics()}catch(n){}return e.state={firebaseConfig:a,db:d.firestore(),livresRef:d.firestore().collection("livres"),value:""},e}return Object(h.a)(t,e),Object(s.a)(t,[{key:"queryCustom",value:function(e){this.setState({value:e.target.value}),console.log("ds query");var t=this.state.livresRef.where("title","==",e.target.value);this.setState({livresRef:t})}},{key:"handleSubmit",value:function(e){e.preventDefault()}},{key:"render",value:function(){var e=this;return r.a.createElement("div",{className:"App"},r.a.createElement("h1",null,"Biblioth\xe8que en ligne"),r.a.createElement("form",{role:"search",onSubmit:function(){return e.handleSubmit}},r.a.createElement("input",{type:"text",title:"Recherche par mots-cl\xe9s",value:this.state.value,id:"rechercher",onChange:function(){return e.queryCustom}})),r.a.createElement(f,{livres:this.state}))}}]),t}(r.a.Component);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));i.a.render(r.a.createElement(m,null),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()}))}},[[15,1,2]]]);
//# sourceMappingURL=main.3e54780b.chunk.js.map