(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[495],{7514:function(e,r,n){"use strict";n.d(r,{Z:function(){return i}});var s=n(5893),t=n(5705),a=n.n(t);function i(){return(0,s.jsxs)("footer",{className:a().footer,children:[(0,s.jsx)("div",{className:"socialbtns",children:(0,s.jsxs)("ul",{children:[(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-facebook"})}),(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-twitter"})}),(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-linkedin"})}),(0,s.jsx)("li",{children:(0,s.jsx)("a",{href:"#",className:"fa fa-lg fa-instagram"})})]})}),(0,s.jsx)("p",{children:"Copyright \xa9  FullStop 2021"})]})}},8571:function(e,r,n){"use strict";n.d(r,{Z:function(){return l}});var s=n(5893),t=[{title:"In\xedcio",url:"/",cName:"nav-links"},{title:"Sobre",url:"/#sobre",cName:"nav-links"},{title:"Programas",url:"#",cName:"nav-links"},{title:"Login",url:"/login",cName:"nav-links"},{title:"Inscreva-se",url:"/registeroption",cName:"nav-links"},{title:"Contato",url:"/#contato",cName:"nav-links"}],a=n(7294),i=n(8485),o=n.n(i),c=n(1664);function l(){var e=(0,a.useState)(!1),r=e[0],n=e[1];return(0,s.jsxs)("nav",{className:o().NavbarItems,children:[(0,s.jsx)("h1",{className:o().navbarlogo,children:"Helpin'Hand"}),(0,s.jsx)("div",{className:o().menuicon,onClick:function(){n(!r)},children:(0,s.jsx)("i",{className:r?"fas fa-times":"fas fa-bars"})}),(0,s.jsx)("ul",{className:r?"nav-menu active":"nav-menu",children:t.map((function(e,r){return(0,s.jsx)("li",{children:(0,s.jsx)(c.default,{href:e.url,children:(0,s.jsx)("a",{className:e.cName,children:e.title})})},r)}))})]})}},6514:function(e,r,n){"use strict";n.r(r),n.d(r,{default:function(){return w}});var s=n(5893),t=n(809),a=n.n(t),i=n(2447),o=n(6265),c=n(9227),l=n(8347),u=n(170),m=n(9384),f=n(3128),d=n(9811),p=n(2986),h=n.n(p),j=n(9008),v=n(8571),x=n(7514),g=n(3138),b=n(1163);function N(e,r){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(e);r&&(s=s.filter((function(r){return Object.getOwnPropertyDescriptor(e,r).enumerable}))),n.push.apply(n,s)}return n}function _(e){for(var r=1;r<arguments.length;r++){var n=null!=arguments[r]?arguments[r]:{};r%2?N(Object(n),!0).forEach((function(r){(0,o.Z)(e,r,n[r])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):N(Object(n)).forEach((function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(n,r))}))}return e}var O=function(e){var r=e.placeholder,n=e.type,t=(0,l.Z)(e,["placeholder","type"]),a=(0,u.U$)(t),i=(0,c.Z)(a,2),o=i[0],f=i[1],d=f.error&&f.touched?f.error:"";return(0,s.jsx)(m.Z,_(_({variant:"outlined",type:n,size:"small",placeholder:r},o),{},{helperText:d,error:!!d,InputLabelProps:{className:h().form}}))},y=d.Ry({username:d.Z_().matches(/^(\S+$)/,"N\xe3o pode conter espa\xe7os").min(5,"O nome da conta deve ter entre 5 a 15 car\xe1teres.").max(15,"O nome da conta deve ter entre 5 a 15 car\xe1teres.").required("Obrigat\xf3rio"),name:d.Z_().required("Obrigat\xf3rio"),email:d.Z_().email("Por favor insira um email v\xe1lido.").required("Obrigat\xf3rio"),password:d.Z_().matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/,"Deve conter no m\xednimo 8 car\xe1teres com pelo menos 1 min\xfascula, 1 mai\xfascula e 1 d\xedgito").required("Obrigat\xf3rio"),confirmation:d.Z_().oneOf([d.iH("password"),null],"Passwords t\xeam de ser iguais.").required("Obrigat\xf3rio")});function w(){var e=(0,b.useRouter)();return(0,s.jsxs)("div",{children:[(0,s.jsx)(j.default,{children:(0,s.jsx)("title",{children:"Register"})}),(0,s.jsx)(v.Z,{}),(0,s.jsxs)("div",{className:h().register,children:[(0,s.jsx)("h1",{children:"Inscreva-se"}),(0,s.jsx)(u.J9,{initialValues:{username:"",name:"",email:"",password:"",confirmation:""},validationSchema:y,onSubmit:function(){var r=(0,i.Z)(a().mark((function r(n,s){var t;return a().wrap((function(r){for(;;)switch(r.prev=r.next){case 0:return t=s.setSubmitting,console.log("submitting"),t(!0),r.next=5,g.h.post("users/insert",n).then((function(e){console.log(JSON.stringify(e.data))})).catch((function(e){console.log(e)}));case 5:t(!1),console.log("submitted"),e.push("/login");case 8:case"end":return r.stop()}}),r)})));return function(e,n){return r.apply(this,arguments)}}(),children:function(e){var r=e.isSubmitting;return(0,s.jsxs)(u.l0,{className:h().form,children:[(0,s.jsx)(O,{className:h().input,placeholder:"username",name:"username",type:"input",as:m.Z}),(0,s.jsx)(O,{placeholder:"first name",name:"name",type:"input",as:m.Z}),(0,s.jsx)(O,{placeholder:"email",name:"email",type:"input",as:m.Z}),(0,s.jsx)(O,{placeholder:"password",name:"password",type:"password",as:m.Z}),(0,s.jsx)(O,{placeholder:"confirm password",name:"confirmation",type:"password",as:m.Z}),(0,s.jsx)("div",{children:(0,s.jsx)(f.Z,{disabled:r,type:"submit",children:"Inscrever-se"})})]})}})]}),(0,s.jsx)(x.Z,{})]})}},2072:function(e,r,n){(window.__NEXT_P=window.__NEXT_P||[]).push(["/register",function(){return n(6514)}])},5705:function(e){e.exports={footer:"styles_footer__3PN-O"}},8485:function(e){e.exports={NavbarItems:"styles_NavbarItems__eFUT0",navbarlogo:"styles_navbarlogo__NMHxL",menuicon:"styles_menuicon__1wXzX"}},2986:function(e){e.exports={register:"register_register__3Hpcz",form:"register_form__3x09Q"}}},function(e){e.O(0,[774,996,313,888,179],(function(){return r=2072,e(e.s=r);var r}));var r=e.O();_N_E=r}]);