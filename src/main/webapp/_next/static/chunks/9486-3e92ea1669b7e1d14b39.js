(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[9486,179],{36808:function(e,n,t){var r,s;!function(a){if(void 0===(s="function"===typeof(r=a)?r.call(n,t,n,e):r)||(e.exports=s),!0,e.exports=a(),!!0){var i=window.Cookies,c=window.Cookies=a();c.noConflict=function(){return window.Cookies=i,c}}}((function(){function e(){for(var e=0,n={};e<arguments.length;e++){var t=arguments[e];for(var r in t)n[r]=t[r]}return n}function n(e){return e.replace(/(%[0-9A-Z]{2})+/g,decodeURIComponent)}return function t(r){function s(){}function a(n,t,a){if("undefined"!==typeof document){"number"===typeof(a=e({path:"/"},s.defaults,a)).expires&&(a.expires=new Date(1*new Date+864e5*a.expires)),a.expires=a.expires?a.expires.toUTCString():"";try{var i=JSON.stringify(t);/^[\{\[]/.test(i)&&(t=i)}catch(l){}t=r.write?r.write(t,n):encodeURIComponent(String(t)).replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g,decodeURIComponent),n=encodeURIComponent(String(n)).replace(/%(23|24|26|2B|5E|60|7C)/g,decodeURIComponent).replace(/[\(\)]/g,escape);var c="";for(var o in a)a[o]&&(c+="; "+o,!0!==a[o]&&(c+="="+a[o].split(";")[0]));return document.cookie=n+"="+t+c}}function i(e,t){if("undefined"!==typeof document){for(var s={},a=document.cookie?document.cookie.split("; "):[],i=0;i<a.length;i++){var c=a[i].split("="),o=c.slice(1).join("=");t||'"'!==o.charAt(0)||(o=o.slice(1,-1));try{var l=n(c[0]);if(o=(r.read||r)(o,l)||n(o),t)try{o=JSON.parse(o)}catch(d){}if(s[l]=o,e===l)break}catch(d){}}return e?s[e]:s}}return s.set=a,s.get=function(e){return i(e,!1)},s.getJSON=function(e){return i(e,!0)},s.remove=function(n,t){a(n,"",e(t,{expires:-1}))},s.defaults={},s.withConverter=t,s}((function(){}))}))},96245:function(e,n,t){"use strict";function r(e){this.message=e}r.prototype=new Error,r.prototype.name="InvalidCharacterError";var s="undefined"!=typeof window&&window.atob&&window.atob.bind(window)||function(e){var n=String(e).replace(/=+$/,"");if(n.length%4==1)throw new r("'atob' failed: The string to be decoded is not correctly encoded.");for(var t,s,a=0,i=0,c="";s=n.charAt(i++);~s&&(t=a%4?64*t+s:s,a++%4)?c+=String.fromCharCode(255&t>>(-2*a&6)):0)s="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(s);return c};function a(e){var n=e.replace(/-/g,"+").replace(/_/g,"/");switch(n.length%4){case 0:break;case 2:n+="==";break;case 3:n+="=";break;default:throw"Illegal base64url string!"}try{return function(e){return decodeURIComponent(s(e).replace(/(.)/g,(function(e,n){var t=n.charCodeAt(0).toString(16).toUpperCase();return t.length<2&&(t="0"+t),"%"+t})))}(n)}catch(e){return s(n)}}function i(e){this.message=e}i.prototype=new Error,i.prototype.name="InvalidTokenError",n.Z=function(e,n){if("string"!=typeof e)throw new i("Invalid token specified");var t=!0===(n=n||{}).header?0:1;try{return JSON.parse(a(e.split(".")[t]))}catch(e){throw new i("Invalid token specified: "+e.message)}}},33138:function(e,n,t){"use strict";t.d(n,{h:function(){return a},d:function(){return i}});var r=t(9669),s=t.n(r),a=s().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/rest/"}),i=s().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/gcs/helpinhand-318217.appspot.com/"})},50851:function(e,n,t){"use strict";t.d(n,{Z:function(){return m}});var r=t(85893),s=t(7623),a=t.n(s),i=t(41664),c=t(7569),o=t(67294),l=t(36808),d=t.n(l),u=t(89583),h=t(60155),p=t(63750),f=t(96245);function m(){var e=(0,o.useContext)(c.V).handleLogout,n=(0,f.Z)(d().getJSON("token")),t=n.role,s=window.location.pathname.replace("/","");return(0,r.jsx)("div",{className:a().Container,children:(0,r.jsx)("div",{className:a().NavbarItems,children:(0,r.jsxs)("div",{className:a().Navbar,children:[(0,r.jsx)(i.default,{href:"/",children:(0,r.jsx)("h1",{className:a().title,children:"HxP"})}),(0,r.jsx)(i.default,{href:"/",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsxs)("span",{className:a().links,children:[(0,r.jsx)(h.wB6,{}),(0,r.jsx)("a",{className:a().linkname,children:" In\xedcio"})]})})}),"USER"==t?(0,r.jsx)(i.default,{href:"/".concat(n.iss),children:(0,r.jsx)("div",{className:s=="".concat(n)?"".concat(a().linkactive):"".concat(a().topics),children:(0,r.jsxs)("span",{className:a().links,children:[(0,r.jsx)(h.eTQ,{}),(0,r.jsx)("a",{className:a().linkname,children:" Perfil"})]})})}):null,(0,r.jsx)(i.default,{href:"/home",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsxs)("span",{className:a().links,children:[(0,r.jsx)(u.uQe,{}),(0,r.jsx)("a",{className:a().linkname,children:" Explorar"})]})})}),(0,r.jsx)(i.default,{href:"/organizations",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsxs)("span",{className:a().links,children:[(0,r.jsx)(p.rVC,{}),(0,r.jsx)("a",{className:a().linkname,children:" Organiza\xe7\xf5es"})]})})}),(0,r.jsx)(i.default,{href:"/createActivity",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsxs)("span",{className:a().links,children:[(0,r.jsx)(h.JEU,{}),(0,r.jsx)("a",{className:a().linkname,children:" Criar Atividade"})]})})}),(0,r.jsx)(i.default,{href:"/rankings",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsxs)("span",{className:a().links,children:[(0,r.jsx)(h.ndN,{}),(0,r.jsx)("a",{className:a().linkname,children:" Rankings"})]})})}),"BO"==t||"ADMIN"==t?(0,r.jsxs)("div",{children:[(0,r.jsx)(i.default,{href:"/global-users",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsx)("span",{className:a().links,children:(0,r.jsx)("a",{className:a().linkname,children:" Utilizadores "})})})}),(0,r.jsx)(i.default,{href:"/create-org",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsx)("span",{className:a().links,children:(0,r.jsx)("a",{className:a().linkname,children:" Criar Org"})})})})]}):null,"ADMIN"==t?(0,r.jsxs)("div",{children:[(0,r.jsx)(i.default,{href:"/create-bo",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsx)("span",{className:a().links,children:(0,r.jsx)("a",{className:a().linkname,children:" Criar Utilizador"})})})}),(0,r.jsx)(i.default,{href:"/bo-users",children:(0,r.jsx)("div",{className:a().topics,children:(0,r.jsx)("span",{className:a().links,children:(0,r.jsx)("a",{className:a().linkname,children:" Staff"})})})})]}):null,(0,r.jsx)("div",{className:a().topics,children:(0,r.jsxs)("span",{className:a().links,children:[(0,r.jsx)(h.p$f,{}),(0,r.jsx)("a",{onClick:function(){window.location.href="/",d().remove("token"),e()},className:a().linkname,children:" Logout"})]})})]})})})}},7569:function(e,n,t){"use strict";t.d(n,{V:function(){return p},H:function(){return f}});var r=t(809),s=t.n(r),a=t(85893),i=t(92447),c=t(67294),o=t(33138),l=t(11163),d=t(36808),u=t.n(d),h=t(96245),p=(0,c.createContext)({});function f(e){var n=e.children,t=(0,l.useRouter)(),r=(0,c.useState)(!1),d=r[0],f=r[1],m=(0,c.useState)(!0),x=m[0],v=m[1],j=(0,c.useState)(!1),g=j[0],k=j[1],N=(0,c.useState)(!1),y=N[0],w=N[1],b=(0,c.useState)([]),_=b[0],C=b[1],O=(0,c.useState)(""),S=O[0],E=O[1];function I(){return(I=(0,i.Z)(s().mark((function e(){return s().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:f(!1);case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function U(){return(U=(0,i.Z)(s().mark((function e(n){return s().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,o.h.post("/authentication/login",n).then((function(e){if(e.data){u().set("token",JSON.stringify(e.data),{expires:1}),f(!0);(0,h.Z)(e.data)}})).catch((function(e){E(e.response.data),f(!1)}));case 2:t.push("/home");case 3:case"end":return e.stop()}}),e)})))).apply(this,arguments)}return(0,c.useEffect)((function(){var e=u().get("token");f(!!e),v(!1)}),[]),x?(0,a.jsx)(a.Fragment,{children:" "}):(0,a.jsx)(p.Provider,{value:{authenticated:d,handleLogin:function(e){return U.apply(this,arguments)},handleLogout:function(){return I.apply(this,arguments)},subAtivity:g,setSubAtivity:k,subEdit:y,setSubEdit:w,keywords:_,setKeywords:C,error:S,setError:E},children:n})}},7623:function(e){e.exports={NavbarItems:"styles_NavbarItems__1mok6",title:"styles_title__3zrvO",Navbar:"styles_Navbar__20cRx",logout:"styles_logout__2F-RO",topics:"styles_topics__3HcYs",links:"styles_links__WJ5oK",linkname:"styles_linkname__YKF9m"}},19708:function(e){e.exports={container:"rankingtables_container__3Fdxu",header:"rankingtables_header__1hc0y",options:"rankingtables_options__39gj9",options_buttons:"rankingtables_options_buttons__30Bpp",scoreTable:"rankingtables_scoreTable__1KAsZ"}},11163:function(e,n,t){e.exports=t(72441)},44405:function(e,n,t){"use strict";t.d(n,{w_:function(){return l}});var r=t(67294),s={color:void 0,size:void 0,className:void 0,style:void 0,attr:void 0},a=r.createContext&&r.createContext(s),i=function(){return(i=Object.assign||function(e){for(var n,t=1,r=arguments.length;t<r;t++)for(var s in n=arguments[t])Object.prototype.hasOwnProperty.call(n,s)&&(e[s]=n[s]);return e}).apply(this,arguments)},c=function(e,n){var t={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&n.indexOf(r)<0&&(t[r]=e[r]);if(null!=e&&"function"===typeof Object.getOwnPropertySymbols){var s=0;for(r=Object.getOwnPropertySymbols(e);s<r.length;s++)n.indexOf(r[s])<0&&Object.prototype.propertyIsEnumerable.call(e,r[s])&&(t[r[s]]=e[r[s]])}return t};function o(e){return e&&e.map((function(e,n){return r.createElement(e.tag,i({key:n},e.attr),o(e.child))}))}function l(e){return function(n){return r.createElement(d,i({attr:i({},e.attr)},n),o(e.child))}}function d(e){var n=function(n){var t,s=e.attr,a=e.size,o=e.title,l=c(e,["attr","size","title"]),d=a||n.size||"1em";return n.className&&(t=n.className),e.className&&(t=(t?t+" ":"")+e.className),r.createElement("svg",i({stroke:"currentColor",fill:"currentColor",strokeWidth:"0"},n.attr,s,l,{className:t,style:i(i({color:e.color||n.color},n.style),e.style),height:d,width:d,xmlns:"http://www.w3.org/2000/svg"}),o&&r.createElement("title",null,o),e.children)};return void 0!==a?r.createElement(a.Consumer,null,(function(e){return n(e)})):n(s)}},14453:function(){}}]);