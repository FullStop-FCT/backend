(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[3459,179],{36808:function(e,n,t){var r,i;!function(o){if(void 0===(i="function"===typeof(r=o)?r.call(n,t,n,e):r)||(e.exports=i),!0,e.exports=o(),!!0){var a=window.Cookies,s=window.Cookies=o();s.noConflict=function(){return window.Cookies=a,s}}}((function(){function e(){for(var e=0,n={};e<arguments.length;e++){var t=arguments[e];for(var r in t)n[r]=t[r]}return n}function n(e){return e.replace(/(%[0-9A-Z]{2})+/g,decodeURIComponent)}return function t(r){function i(){}function o(n,t,o){if("undefined"!==typeof document){"number"===typeof(o=e({path:"/"},i.defaults,o)).expires&&(o.expires=new Date(1*new Date+864e5*o.expires)),o.expires=o.expires?o.expires.toUTCString():"";try{var a=JSON.stringify(t);/^[\{\[]/.test(a)&&(t=a)}catch(u){}t=r.write?r.write(t,n):encodeURIComponent(String(t)).replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g,decodeURIComponent),n=encodeURIComponent(String(n)).replace(/%(23|24|26|2B|5E|60|7C)/g,decodeURIComponent).replace(/[\(\)]/g,escape);var s="";for(var c in o)o[c]&&(s+="; "+c,!0!==o[c]&&(s+="="+o[c].split(";")[0]));return document.cookie=n+"="+t+s}}function a(e,t){if("undefined"!==typeof document){for(var i={},o=document.cookie?document.cookie.split("; "):[],a=0;a<o.length;a++){var s=o[a].split("="),c=s.slice(1).join("=");t||'"'!==c.charAt(0)||(c=c.slice(1,-1));try{var u=n(s[0]);if(c=(r.read||r)(c,u)||n(c),t)try{c=JSON.parse(c)}catch(l){}if(i[u]=c,e===u)break}catch(l){}}return e?i[e]:i}}return i.set=o,i.get=function(e){return a(e,!1)},i.getJSON=function(e){return a(e,!0)},i.remove=function(n,t){o(n,"",e(t,{expires:-1}))},i.defaults={},i.withConverter=t,i}((function(){}))}))},96245:function(e,n,t){"use strict";function r(e){this.message=e}r.prototype=new Error,r.prototype.name="InvalidCharacterError";var i="undefined"!=typeof window&&window.atob&&window.atob.bind(window)||function(e){var n=String(e).replace(/=+$/,"");if(n.length%4==1)throw new r("'atob' failed: The string to be decoded is not correctly encoded.");for(var t,i,o=0,a=0,s="";i=n.charAt(a++);~i&&(t=o%4?64*t+i:i,o++%4)?s+=String.fromCharCode(255&t>>(-2*o&6)):0)i="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(i);return s};function o(e){var n=e.replace(/-/g,"+").replace(/_/g,"/");switch(n.length%4){case 0:break;case 2:n+="==";break;case 3:n+="=";break;default:throw"Illegal base64url string!"}try{return function(e){return decodeURIComponent(i(e).replace(/(.)/g,(function(e,n){var t=n.charCodeAt(0).toString(16).toUpperCase();return t.length<2&&(t="0"+t),"%"+t})))}(n)}catch(e){return i(n)}}function a(e){this.message=e}a.prototype=new Error,a.prototype.name="InvalidTokenError",n.Z=function(e,n){if("string"!=typeof e)throw new a("Invalid token specified");var t=!0===(n=n||{}).header?0:1;try{return JSON.parse(o(e.split(".")[t]))}catch(e){throw new a("Invalid token specified: "+e.message)}}},33138:function(e,n,t){"use strict";t.d(n,{h:function(){return o},d:function(){return a}});var r=t(9669),i=t.n(r),o=i().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/rest/"}),a=i().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/gcs/helpinhand-318217.appspot.com/"})},77514:function(e,n,t){"use strict";t.d(n,{Z:function(){return a}});var r=t(85893),i=t(20255),o=t.n(i);function a(){return(0,r.jsxs)("footer",{className:o().footer,children:[(0,r.jsx)("div",{className:"socialbtns",children:(0,r.jsxs)("ul",{children:[(0,r.jsx)("li",{children:(0,r.jsx)("a",{href:"#",className:"fa fa-lg fa-facebook"})}),(0,r.jsx)("li",{children:(0,r.jsx)("a",{href:"#",className:"fa fa-lg fa-twitter"})}),(0,r.jsx)("li",{children:(0,r.jsx)("a",{href:"#",className:"fa fa-lg fa-linkedin"})}),(0,r.jsx)("li",{children:(0,r.jsx)("a",{href:"#",className:"fa fa-lg fa-instagram"})})]})}),(0,r.jsx)("p",{children:"Copyright \xa9  FullStop 2021"})]})}},38571:function(e,n,t){"use strict";t.d(n,{Z:function(){return u}});var r=t(85893),i=[{title:"In\xedcio",url:"/#top",cName:"nav-links"},{title:"\xc1rea de Utilizador",url:"/home",cName:"nav-links"},{title:"Login",url:"/login",cName:"nav-links"},{title:"Registar",url:"/registeroption",cName:"nav-links"},{title:"Sobre",url:"/#sobre",cName:"nav-links"},{title:"Contatos",url:"/#contatos",cName:"nav-links"}],o=t(67294),a=t(63106),s=t.n(a),c=t(41664);function u(){var e=(0,o.useState)(!1),n=e[0],t=e[1];return(0,r.jsxs)("nav",{className:s().NavbarItems,children:[(0,r.jsx)("a",{href:"/",children:(0,r.jsx)("img",{className:s().brand_image,src:"/red_logo.png"})}),(0,r.jsx)("div",{className:s().menuicon,onClick:function(){t(!n)},children:(0,r.jsx)("i",{className:n?"fas fa-times":"fas fa-bars"})}),(0,r.jsx)("ul",{className:n?"nav-menu active":"nav-menu",children:i.map((function(e,n){return(0,r.jsx)("li",{children:(0,r.jsx)(c.default,{href:e.url,children:(0,r.jsx)("a",{className:e.cName,children:e.title})})},n)}))})]})}},7569:function(e,n,t){"use strict";t.d(n,{V:function(){return p},H:function(){return h}});var r=t(809),i=t.n(r),o=t(85893),a=t(92447),s=t(67294),c=t(33138),u=t(11163),l=t(36808),f=t.n(l),d=t(96245),p=(0,s.createContext)({});function h(e){var n=e.children,t=(0,u.useRouter)(),r=(0,s.useState)(!1),l=r[0],h=r[1],m=(0,s.useState)(!0),g=m[0],v=m[1],x=(0,s.useState)(!1),w=x[0],j=x[1],b=(0,s.useState)(!1),y=b[0],_=b[1],N=(0,s.useState)([]),k=N[0],S=N[1],O=(0,s.useState)(""),C=O[0],Z=O[1];function E(){return(E=(0,a.Z)(i().mark((function e(){return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:h(!1);case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function P(){return(P=(0,a.Z)(i().mark((function e(n){return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,c.h.post("/authentication/login",n).then((function(e){if(e.data){f().set("token",JSON.stringify(e.data),{expires:1}),h(!0);(0,d.Z)(e.data)}})).catch((function(e){Z(e.response.data),h(!1)}));case 2:t.push("/home");case 3:case"end":return e.stop()}}),e)})))).apply(this,arguments)}return(0,s.useEffect)((function(){var e=f().get("token");h(!!e),v(!1)}),[]),g?(0,o.jsx)(o.Fragment,{children:" "}):(0,o.jsx)(p.Provider,{value:{authenticated:l,handleLogin:function(e){return P.apply(this,arguments)},handleLogout:function(){return E.apply(this,arguments)},subAtivity:w,setSubAtivity:j,subEdit:y,setSubEdit:_,keywords:k,setKeywords:S,error:C,setError:Z},children:n})}},33204:function(e,n,t){"use strict";t.r(n),t.d(n,{default:function(){return Z}});var r=t(85893),i=t(809),o=t.n(i),a=t(92447),s=t(26265),c=t(64121),u=t(38347),l=t(25118),f=t(7784),d=t(282),p=t(19501),h=t(72167),m=t.n(h),g=t(9008),v=t(38571),x=t(7569),w=t(67294),j=t(11163),b=t(36808),y=t.n(b),_=t(41664),N=t(96245),k=t(77514);function S(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);n&&(r=r.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,r)}return t}function O(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?S(Object(t),!0).forEach((function(n){(0,s.Z)(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):S(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}var C=p.Ry({username:p.Z_().required("Obrigat\xf3rio"),password:p.Z_().required("Obrigat\xf3rio"),lastName:p.Z_()});function Z(){y().getJSON("token")&&(0,N.Z)(y().getJSON("token"));(0,j.useRouter)();var e=(0,w.useContext)(x.V),n=e.handleLogin,t=e.error,i=(0,w.useState)(!1),s=i[0],p=i[1],h=(0,w.useState)(!1),b=h[0],S=h[1],Z=function(e){var n=e.type,t=e.placeholder,i=(0,u.Z)(e,["type","placeholder"]),o=(0,l.U$)(i),a=(0,c.Z)(o,2),s=a[0],d=a[1],p=d.error&&d.touched?d.error:"";return(0,r.jsx)(f.Z,O(O({variant:"outlined",type:n,size:"small",placeholder:t},s),{},{helperText:p,onClick:function(){return S(!1)},error:!!p,InputLabelProps:{className:m().form}}))};return(0,r.jsxs)("div",{children:[(0,r.jsxs)("div",{children:[(0,r.jsx)(g.default,{children:(0,r.jsx)("title",{children:"Login"})}),(0,r.jsx)(v.Z,{}),(0,r.jsx)("div",{className:m().login,children:s?(0,r.jsx)("a",{children:"Por favor confirme o seu email. Caso n\xe3o o encontre, verifique a sua caixa de spam. "}):(0,r.jsxs)(r.Fragment,{children:[(0,r.jsx)("h1",{children:"Login"}),(0,r.jsx)(l.J9,{initialValues:{username:"",password:""},validationSchema:C,onSubmit:function(){var e=(0,a.Z)(o().mark((function e(r,i){var a;return o().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:(a=i.setSubmitting)(!0),"email"==t?p(!0):"inputerror"==t?S(!0):(n(r),a(!1));case 3:case"end":return e.stop()}}),e)})));return function(n,t){return e.apply(this,arguments)}}(),children:function(e){var n=e.isSubmitting;return(0,r.jsxs)(l.l0,{className:m().form,children:[b?(0,r.jsx)("a",{className:m().erro,children:"Por favor verifique se os dados que inseriu est\xe3o corretos."}):null,(0,r.jsx)(Z,{className:m().input,placeholder:"Nome de utilizador",name:"username",type:"input",as:f.Z}),(0,r.jsx)("br",{}),(0,r.jsx)(Z,{placeholder:"Password",name:"password",type:"password",as:f.Z}),(0,r.jsx)("br",{}),(0,r.jsx)(_.default,{href:"/forgot-password",children:(0,r.jsx)("a",{className:m().link,children:"Esqueceu-se da password?"})}),(0,r.jsx)("div",{children:(0,r.jsx)(d.Z,{disabled:n,type:"submit",children:"Login"})})]})}})]})})]}),(0,r.jsx)(k.Z,{})]})}},87237:function(e,n,t){(window.__NEXT_P=window.__NEXT_P||[]).push(["/login",function(){return t(33204)}])},20255:function(e){e.exports={footer:"styles_footer__3PN-O"}},63106:function(e){e.exports={NavbarItems:"styles_NavbarItems__eFUT0",brand_image:"styles_brand_image__3Jyxl",navbarlogo:"styles_navbarlogo__NMHxL",menuicon:"styles_menuicon__1wXzX"}},72167:function(e){e.exports={login:"login_login__PAeaN",form:"login_form__3VGms",erro:"login_erro__2SsQ9",link:"login_link__37Ldx"}},11163:function(e,n,t){e.exports=t(72441)},14453:function(){}},function(e){e.O(0,[9774,7925,3433,7321,3996,8540,6751],(function(){return n=87237,e(e.s=n);var n}));var n=e.O();_N_E=n}]);