(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[5405,179],{77514:function(e,n,r){"use strict";r.d(n,{Z:function(){return a}});var s=r(85893),t=r(20255),i=r.n(t);function a(){return(0,s.jsxs)("footer",{className:i().footer,children:[(0,s.jsx)("div",{className:"socialbtns",children:(0,s.jsxs)("ul",{children:[(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-facebook"})}),(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-twitter"})}),(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-linkedin"})}),(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-instagram"})})]})}),(0,s.jsx)("p",{children:"Copyright \xa9  FullStop 2021"})]})}},38571:function(e,n,r){"use strict";r.d(n,{Z:function(){return c}});var s=r(85893),t=[{title:"In\xedcio",url:"/#top",cName:"nav-links"},{title:"\xc1rea de Utilizador",url:"/home",cName:"nav-links"},{title:"Login",url:"/login",cName:"nav-links"},{title:"Registar",url:"/registeroption",cName:"nav-links"},{title:"Sobre",url:"/#sobre",cName:"nav-links"},{title:"Contatos",url:"/#contatos",cName:"nav-links"}],i=r(67294),a=r(63106),o=r.n(a),l=r(41664);function c(){var e=(0,i.useState)(!1),n=e[0],r=e[1];return(0,s.jsxs)("nav",{className:o().NavbarItems,children:[(0,s.jsx)("a",{href:"/",children:(0,s.jsx)("img",{className:o().brand_image,src:"/red_logo.png"})}),(0,s.jsx)("div",{className:o().menuicon,onClick:function(){r(!n)},children:(0,s.jsx)("i",{className:n?"fas fa-times":"fas fa-bars"})}),(0,s.jsx)("ul",{className:n?"nav-menu active":"nav-menu",children:t.map((function(e,n){return(0,s.jsx)("li",{children:(0,s.jsx)(l.default,{href:e.url,children:(0,s.jsx)("a",{className:e.cName,children:e.title})})},n)}))})]})}},67175:function(e,n,r){"use strict";r.r(n),r.d(n,{default:function(){return I}});var s=r(85893),t=r(45586),i=r.n(t),a=r(38571),o=r(9008),l=r(809),c=r.n(l),d=r(92447),u=r(26265),m=r(64121),f=r(38347),p=r(9117),h=r.n(p),x=r(67294),j=r(19501),v=r(7784),_=r(282),g=r(25118),b=r(11163);function y(e,n){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(e);n&&(s=s.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),r.push.apply(r,s)}return r}function N(e){for(var n=1;n<arguments.length;n++){var r=null!=arguments[n]?arguments[n]:{};n%2?y(Object(r),!0).forEach((function(n){(0,u.Z)(e,n,r[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):y(Object(r)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(r,n))}))}return e}var O=function(e){var n=e.type,r=e.placeholder,t=(0,f.Z)(e,["type","placeholder"]),i=(0,g.U$)(t),a=(0,m.Z)(i,2),o=a[0],l=a[1],c=l.error&&l.touched?l.error:"";return(0,s.jsx)(v.Z,N(N({variant:"outlined",type:n,size:"small",placeholder:r},o),{},{helperText:c,error:!!c,className:h().multiline}))},Z=function(e){var n=e.type,r=e.placeholder,t=(0,f.Z)(e,["type","placeholder"]),i=(0,g.U$)(t),a=(0,m.Z)(i,2),o=a[0],l=a[1],c=l.error&&l.touched?l.error:"";return(0,s.jsx)(v.Z,N(N({rows:4,variant:"outlined",multiline:!0,type:n,size:"small",placeholder:r},o),{},{helperText:c,error:!!c,className:h().multiline}))},w=j.Ry({nome:j.Z_().required("Obrigat\xf3rio"),email:j.Z_().email("Por favor insira um email v\xe1lido.").required("Obrigat\xf3rio"),assunto:j.Z_().max(15,"O assunto pode ter at\xe9 15 caracteres").required("Obrigatorio"),mensagem:j.Z_().max(100,"A mensagem pode ter no maximo 100 caracteres").required("Obrigatorio")});function k(){var e=(0,x.useState)(!1),n=e[0],r=e[1],t=(0,b.useRouter)();return(0,s.jsxs)("div",{className:h().Container,children:[(0,s.jsxs)("div",{className:h().contato,children:[(0,s.jsx)("h1",{children:"Contato"}),(0,s.jsx)("p",{children:"hxp@fullstop.website"})]}),(0,s.jsxs)("div",{className:h().feedback,children:[(0,s.jsx)("h1",{children:"Fale connosco"}),(0,s.jsx)(g.J9,{initialValues:{nome:"",email:"",assunto:"",mensagem:""},validationSchema:w,onSubmit:function(){var e=(0,d.Z)(c().mark((function e(n,s){var i,a;return c().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:i=s.setSubmitting,a=s.resetForm,r(!0),i(!0),fetch("/api/contact",{method:"post",body:JSON.stringify(n)}),console.log(""),i(!1),r(!1),a(),t.push("/");case 9:case"end":return e.stop()}}),e)})));return function(n,r){return e.apply(this,arguments)}}(),children:function(e){var r=e.isSubmitting;return(0,s.jsxs)(g.l0,{className:h().form,id:"form",children:[(0,s.jsx)(O,{placeholder:"Nome",name:"nome",type:"input",as:v.Z}),(0,s.jsx)("br",{}),(0,s.jsx)(O,{placeholder:"Email",name:"email",type:"input",as:v.Z}),(0,s.jsx)("br",{}),(0,s.jsx)(O,{placeholder:"Assunto",name:"assunto",type:"input",as:v.Z}),(0,s.jsx)("br",{}),(0,s.jsx)(Z,{placeholder:"Mensagem",name:"mensagem",type:"input"}),(0,s.jsxs)("div",{children:[(0,s.jsx)(_.Z,{disabled:r,type:"submit",children:"Enviar"}),(0,s.jsx)("div",{className:h().loading,children:n?(0,s.jsx)("img",{src:"/loadingfeedback.gif",alt:"loading"}):(0,s.jsx)(s.Fragment,{children:" "})})]})]})}})]})]})}var P=r(77514),C=r(41664),S=r(72003),E=r.n(S);function q(){return(0,s.jsx)("div",{className:E().container1,children:(0,s.jsxs)("div",{className:E().container2,children:[(0,s.jsx)("h1",{children:"Mais Sobre N\xf3s"}),(0,s.jsxs)("p",{children:["A Helping XPerience \xe9 um programa de voluntariado especial que visa a envolver e inspirar. Desde 2021, possibilitamos a in\xfameros participantes",(0,s.jsx)("br",{})," experi\xeancias envolventes que eles levam consigo para o resto de suas vidas. Com uma s\xe9rie de programas dispon\xedveis para diferentes n\xedveis",(0,s.jsx)("br",{})," de habilidades e interesses, h\xe1 algo para todos desfrutarem."]}),(0,s.jsx)("button",{children:(0,s.jsx)(C.default,{href:"/registeroption",children:(0,s.jsx)("a",{children:"Inscrever-se"})})})]})})}function I(){return(0,s.jsxs)("div",{children:[(0,s.jsx)(o.default,{children:(0,s.jsx)("title",{children:"Helping XPerience"})}),(0,s.jsx)(a.Z,{}),(0,s.jsxs)("div",{className:i().introContainer,id:"top",children:[(0,s.jsx)("div",{className:i().div1image,children:(0,s.jsx)("img",{src:"/pessoas-parque.png",alt:"Pessoas no parque"})}),(0,s.jsxs)("div",{className:i().divIntro,children:[(0,s.jsx)("h1",{children:(0,s.jsx)("span",{children:"Helping XPerience"})}),(0,s.jsx)("p",{children:"Foco na sua ess\xeancia"})]})]}),(0,s.jsx)("div",{id:"sobre",children:(0,s.jsx)(q,{})}),(0,s.jsx)("div",{id:"contatos",children:(0,s.jsx)(k,{})}),(0,s.jsx)("div",{className:i().footer,children:(0,s.jsx)(P.Z,{})})]})}},45301:function(e,n,r){(window.__NEXT_P=window.__NEXT_P||[]).push(["/",function(){return r(67175)}])},9117:function(e){e.exports={Container:"styles_Container__20NHm",contato:"styles_contato__1jc2L",feedback:"styles_feedback__1XlbJ",form:"styles_form__3_Mzn",multiline:"styles_multiline__2QWAr",loading:"styles_loading__2ShC4"}},20255:function(e){e.exports={footer:"styles_footer__3PN-O"}},63106:function(e){e.exports={NavbarItems:"styles_NavbarItems__eFUT0",brand_image:"styles_brand_image__3Jyxl",navbarlogo:"styles_navbarlogo__NMHxL",menuicon:"styles_menuicon__1wXzX"}},72003:function(e){e.exports={container1:"styles_container1__1114w",container2:"styles_container2__342St"}},45586:function(e){e.exports={introContainer:"styles_introContainer__12ZgK",div1image:"styles_div1image__2tndz",divIntro:"styles_divIntro__3DDRO",footer:"styles_footer__3lPed"}},92447:function(e,n,r){"use strict";function s(e,n,r,s,t,i,a){try{var o=e[i](a),l=o.value}catch(c){return void r(c)}o.done?n(l):Promise.resolve(l).then(s,t)}function t(e){return function(){var n=this,r=arguments;return new Promise((function(t,i){var a=e.apply(n,r);function o(e){s(a,t,i,o,l,"next",e)}function l(e){s(a,t,i,o,l,"throw",e)}o(void 0)}))}}r.d(n,{Z:function(){return t}})},11163:function(e,n,r){e.exports=r(72441)},14453:function(){}},function(e){e.O(0,[9774,7925,3433,3996,8540,6751],(function(){return n=45301,e(e.s=n);var n}));var n=e.O();_N_E=n}]);