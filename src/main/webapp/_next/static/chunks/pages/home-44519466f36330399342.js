(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[229],{7504:function(e,t,r){"use strict";r.d(t,{Z:function(){return h}});var n=r(5893),i=r(6808),a=r.n(i)().get("user"),s=[{title:"Home",url:"/home",cName:"nav-links"},{title:"Explore",url:"/home",cName:"nav-links"},{title:"Profile",url:"/users/".concat(a),cName:"nav-links"},{title:"Notification",url:"/home",cName:"nav-links"},{title:"More",url:"/home",cName:"nav-links"}],o=r(7294),c=r(3056),l=r.n(c),u=r(1664),d=r(7569);function h(){var e=(0,o.useState)(!1),t=(e[0],e[1],(0,o.useContext)(d.V).handleLogout);return(0,n.jsx)("div",{className:l().Container,children:(0,n.jsx)("div",{className:l().NavbarItems,children:(0,n.jsxs)("nav",{children:[(0,n.jsx)("h1",{children:"Helpin'Hand"}),(0,n.jsxs)("div",{className:l().Navbar,children:[(0,n.jsx)("ul",{children:s.map((function(e,t){return(0,n.jsx)("li",{children:(0,n.jsx)(u.default,{href:e.url,children:(0,n.jsx)("a",{className:e.cName,children:e.title})})},t)}))}),(0,n.jsx)("button",{onClick:function(){return t()},children:"Logout"})]})]})})})}},1610:function(e,t,r){"use strict";r.r(t),r.d(t,{__N_SSP:function(){return E},default:function(){return H}});var n=r(5893),i=r(7321),a=r.n(i),s=r(9008),o=r(7504),c=r(809),l=r.n(c),u=r(2447),d=r(6265),h=r(9227),p=r(8347),m=r(170),x=r(9384),v=r(3128),j=r(9811),f=r(3138),_=r(7569),y=r(7294),b=r(1163),O=r(6808),g=r.n(O),N=r(7130),Z=r.n(N);function P(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function k(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?P(Object(r),!0).forEach((function(t){(0,d.Z)(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):P(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}var w=function(e){var t=e.type,r=e.placeholder,i=(0,p.Z)(e,["type","placeholder"]),a=(0,m.U$)(i),s=(0,h.Z)(a,2),o=s[0],c=s[1],l=c.error&&c.touched?c.error:"";return(0,n.jsx)(x.Z,k(k({variant:"outlined",type:t,size:"small",placeholder:r},o),{},{helperText:l,error:!!l,InputLabelProps:{className:Z().form}}))},C=j.Ry({title:j.Z_().min(10,"O t\xedtulo deve ter entre 10 a 50 car\xe1teres.").max(50,"O t\xedtulo deve ter entre 10 a 50 car\xe1teres.").required("Obrigat\xf3rio"),description:j.Z_().min(10,"A descri\xe7\xe3o deve conter no min\xedmo 100 car\xe1teres.").required("Obrigat\xf3rio"),date:j.Z_().required("Obrigat\xf3rio"),location:j.Z_().required("Obrigat\xf3rio"),totalParticipants:j.Rx().moreThan(0,"Uma atividade precisa de pelo menos uma pessoa.").typeError("N\xe3o pode conter letras.").required("Obrigat\xf3rio"),category:j.Z_().required("Obrigat\xf3rio")});function S(){var e=(0,b.useRouter)(),t=(0,y.useContext)(_.V),r=t.subAtivity,i=t.setSubAtivity,a=(0,y.useContext)(_.V).authenticated;return(0,n.jsxs)("div",{children:[(0,n.jsx)(s.default,{children:(0,n.jsx)("title",{children:"Login"})}),(0,n.jsx)("div",{className:Z().register,children:(0,n.jsx)(m.J9,{initialValues:{title:"",description:"",date:"",location:"",totalParticipants:0,category:"",activityOwner:""},validationSchema:C,onSubmit:function(){var t=(0,u.Z)(l().mark((function t(n,s){var o,c,u,d;return l().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:if(o=s.setSubmitting,console.log("submitting"),o(!0),c={headers:{"Content-Type":"application/json"}},u=g().get("token"),d=JSON.stringify({token:k({},JSON.parse(u)),activityData:k({},n)}),console.log(d),console.log(a),!a){t.next=11;break}return t.next=11,f.h.post("activities/insert",d,c).then((function(e){i(!r),console.log(e.data)})).catch((function(e){console.log(e)}));case 11:o(!1),e.push("/home");case 13:case"end":return t.stop()}}),t)})));return function(e,r){return t.apply(this,arguments)}}(),children:function(e){var t=e.isSubmitting;return(0,n.jsxs)(m.l0,{className:Z().form,children:[(0,n.jsx)(w,{placeholder:"title",name:"title",type:"input",as:x.Z}),(0,n.jsx)(w,{placeholder:"description",name:"description",type:"input",as:x.Z}),(0,n.jsx)(w,{placeholder:"date",name:"date",type:"input",as:x.Z}),(0,n.jsx)(w,{placeholder:"location",name:"location",type:"input",as:x.Z}),(0,n.jsx)(w,{placeholder:"totalParticipants",name:"totalParticipants",type:"input",as:x.Z}),(0,n.jsx)(w,{placeholder:"category",name:"category",type:"input",as:x.Z}),(0,n.jsx)(w,{placeholder:"activityOwner",name:"activityOwner",type:"input",as:x.Z}),(0,n.jsx)("div",{children:(0,n.jsx)(v.Z,{disabled:t,type:"submit",children:"submit"})})]})}})})]})}var E=!0;function H(e){console.log(e.data);var t=(0,y.useContext)(_.V),r=t.subAtivity,i=t.setSubAtivity;return(0,n.jsxs)("div",{className:a().Container,children:[(0,n.jsx)(s.default,{children:(0,n.jsx)("title",{children:"Home"})}),(0,n.jsx)("div",{className:a().Header,children:(0,n.jsx)(o.Z,{})}),(0,n.jsxs)("div",{className:a().Feed,children:[(0,n.jsx)("div",{className:a().home,children:(0,n.jsx)("h1",{children:"Home"})}),(0,n.jsx)("button",{onClick:function(){return i(!r)},children:"criar atividade"}),(0,n.jsx)("div",{children:r?(0,n.jsx)(S,{}):(0,n.jsx)(n.Fragment,{})}),(0,n.jsxs)("ul",{children:[e.data.map((function(e,t){return(0,n.jsxs)("div",{className:a().ativity,children:[(0,n.jsxs)("li",{children:[(0,n.jsx)("p",{children:e.title}),(0,n.jsx)("p",{children:e.description}),(0,n.jsx)("p",{children:e.date}),(0,n.jsx)("p",{children:e.location}),(0,n.jsx)("p",{children:e.totalParticipants}),(0,n.jsx)("p",{children:e.activityOwner}),(0,n.jsx)("p",{children:e.category})]},e.title)," "]})})),(0,n.jsx)("br",{})]})]}),(0,n.jsx)("div",{className:a().Other})]})}},1344:function(e,t,r){(window.__NEXT_P=window.__NEXT_P||[]).push(["/home",function(){return r(1610)}])},7130:function(e){e.exports={register:"styles_register__3Pve7",form:"styles_form__2CoOj"}},3056:function(e){e.exports={NavbarItems:"styles_NavbarItems__1mok6",Navbar:"styles_Navbar__20cRx"}},7321:function(e){e.exports={Container:"home_Container__3M0xW",Header:"home_Header__2l9bK",Feed:"home_Feed__1Zhrf",home:"home_home__1XkTP",ativity:"home_ativity__9EBnA",Other:"home_Other__iN9DO"}}},function(e){e.O(0,[774,996,313,888,179],(function(){return t=1344,e(e.s=t);var t}));var t=e.O();_N_E=t}]);