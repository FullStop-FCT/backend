(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[2222,179],{36808:function(e,n,t){var r,a;!function(s){if(void 0===(a="function"===typeof(r=s)?r.call(n,t,n,e):r)||(e.exports=a),!0,e.exports=s(),!!0){var i=window.Cookies,c=window.Cookies=s();c.noConflict=function(){return window.Cookies=i,c}}}((function(){function e(){for(var e=0,n={};e<arguments.length;e++){var t=arguments[e];for(var r in t)n[r]=t[r]}return n}function n(e){return e.replace(/(%[0-9A-Z]{2})+/g,decodeURIComponent)}return function t(r){function a(){}function s(n,t,s){if("undefined"!==typeof document){"number"===typeof(s=e({path:"/"},a.defaults,s)).expires&&(s.expires=new Date(1*new Date+864e5*s.expires)),s.expires=s.expires?s.expires.toUTCString():"";try{var i=JSON.stringify(t);/^[\{\[]/.test(i)&&(t=i)}catch(l){}t=r.write?r.write(t,n):encodeURIComponent(String(t)).replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g,decodeURIComponent),n=encodeURIComponent(String(n)).replace(/%(23|24|26|2B|5E|60|7C)/g,decodeURIComponent).replace(/[\(\)]/g,escape);var c="";for(var o in s)s[o]&&(c+="; "+o,!0!==s[o]&&(c+="="+s[o].split(";")[0]));return document.cookie=n+"="+t+c}}function i(e,t){if("undefined"!==typeof document){for(var a={},s=document.cookie?document.cookie.split("; "):[],i=0;i<s.length;i++){var c=s[i].split("="),o=c.slice(1).join("=");t||'"'!==o.charAt(0)||(o=o.slice(1,-1));try{var l=n(c[0]);if(o=(r.read||r)(o,l)||n(o),t)try{o=JSON.parse(o)}catch(u){}if(a[l]=o,e===l)break}catch(u){}}return e?a[e]:a}}return a.set=s,a.get=function(e){return i(e,!1)},a.getJSON=function(e){return i(e,!0)},a.remove=function(n,t){s(n,"",e(t,{expires:-1}))},a.defaults={},a.withConverter=t,a}((function(){}))}))},96245:function(e,n,t){"use strict";function r(e){this.message=e}r.prototype=new Error,r.prototype.name="InvalidCharacterError";var a="undefined"!=typeof window&&window.atob&&window.atob.bind(window)||function(e){var n=String(e).replace(/=+$/,"");if(n.length%4==1)throw new r("'atob' failed: The string to be decoded is not correctly encoded.");for(var t,a,s=0,i=0,c="";a=n.charAt(i++);~a&&(t=s%4?64*t+a:a,s++%4)?c+=String.fromCharCode(255&t>>(-2*s&6)):0)a="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(a);return c};function s(e){var n=e.replace(/-/g,"+").replace(/_/g,"/");switch(n.length%4){case 0:break;case 2:n+="==";break;case 3:n+="=";break;default:throw"Illegal base64url string!"}try{return function(e){return decodeURIComponent(a(e).replace(/(.)/g,(function(e,n){var t=n.charCodeAt(0).toString(16).toUpperCase();return t.length<2&&(t="0"+t),"%"+t})))}(n)}catch(e){return a(n)}}function i(e){this.message=e}i.prototype=new Error,i.prototype.name="InvalidTokenError",n.Z=function(e,n){if("string"!=typeof e)throw new i("Invalid token specified");var t=!0===(n=n||{}).header?0:1;try{return JSON.parse(s(e.split(".")[t]))}catch(e){throw new i("Invalid token specified: "+e.message)}}},16071:function(e,n,t){"use strict";var r=t(53848),a=t(69448);n.default=void 0;var s=a(t(67294)),i=t(11689),c=t(72441),o=t(75749),l={};function u(e,n,t,r){if(e&&(0,i.isLocalURL)(n)){e.prefetch(n,t,r).catch((function(e){0}));var a=r&&"undefined"!==typeof r.locale?r.locale:e&&e.locale;l[n+"%"+t+(a?"%"+a:"")]=!0}}var d=function(e){var n=!1!==e.prefetch,t=(0,c.useRouter)(),a=t&&t.pathname||"/",d=s.default.useMemo((function(){var n=(0,i.resolveHref)(a,e.href,!0),t=r(n,2),s=t[0],c=t[1];return{href:s,as:e.as?(0,i.resolveHref)(a,e.as):c||s}}),[a,e.href,e.as]),f=d.href,p=d.as,h=e.children,m=e.replace,v=e.shallow,x=e.scroll,j=e.locale;"string"===typeof h&&(h=s.default.createElement("a",null,h));var y=s.Children.only(h),g=y&&"object"===typeof y&&y.ref,k=(0,o.useIntersection)({rootMargin:"200px"}),N=r(k,2),w=N[0],b=N[1],_=s.default.useCallback((function(e){w(e),g&&("function"===typeof g?g(e):"object"===typeof g&&(g.current=e))}),[g,w]);(0,s.useEffect)((function(){var e=b&&n&&(0,i.isLocalURL)(f),r="undefined"!==typeof j?j:t&&t.locale,a=l[f+"%"+p+(r?"%"+r:"")];e&&!a&&u(t,f,p,{locale:r})}),[p,f,b,j,n,t]);var C={ref:_,onClick:function(e){y.props&&"function"===typeof y.props.onClick&&y.props.onClick(e),e.defaultPrevented||function(e,n,t,r,a,s,c,o){("A"!==e.currentTarget.nodeName||!function(e){var n=e.currentTarget.target;return n&&"_self"!==n||e.metaKey||e.ctrlKey||e.shiftKey||e.altKey||e.nativeEvent&&2===e.nativeEvent.which}(e)&&(0,i.isLocalURL)(t))&&(e.preventDefault(),null==c&&(c=r.indexOf("#")<0),n[a?"replace":"push"](t,r,{shallow:s,locale:o,scroll:c}))}(e,t,f,p,m,v,x,j)},onMouseEnter:function(e){(0,i.isLocalURL)(f)&&(y.props&&"function"===typeof y.props.onMouseEnter&&y.props.onMouseEnter(e),u(t,f,p,{priority:!0}))}};if(e.passHref||"a"===y.type&&!("href"in y.props)){var E="undefined"!==typeof j?j:t&&t.locale,O=t&&t.isLocaleDomain&&(0,i.getDomainLocale)(p,E,t&&t.locales,t&&t.domainLocales);C.href=O||(0,i.addBasePath)((0,i.addLocale)(p,E,t&&t.defaultLocale))}return s.default.cloneElement(y,C)};n.default=d},75749:function(e,n,t){"use strict";var r=t(53848);n.__esModule=!0,n.useIntersection=function(e){var n=e.rootMargin,t=e.disabled||!i,o=(0,a.useRef)(),l=(0,a.useState)(!1),u=r(l,2),d=u[0],f=u[1],p=(0,a.useCallback)((function(e){o.current&&(o.current(),o.current=void 0),t||d||e&&e.tagName&&(o.current=function(e,n,t){var r=function(e){var n=e.rootMargin||"",t=c.get(n);if(t)return t;var r=new Map,a=new IntersectionObserver((function(e){e.forEach((function(e){var n=r.get(e.target),t=e.isIntersecting||e.intersectionRatio>0;n&&t&&n(t)}))}),e);return c.set(n,t={id:n,observer:a,elements:r}),t}(t),a=r.id,s=r.observer,i=r.elements;return i.set(e,n),s.observe(e),function(){i.delete(e),s.unobserve(e),0===i.size&&(s.disconnect(),c.delete(a))}}(e,(function(e){return e&&f(e)}),{rootMargin:n}))}),[t,n,d]);return(0,a.useEffect)((function(){if(!i&&!d){var e=(0,s.requestIdleCallback)((function(){return f(!0)}));return function(){return(0,s.cancelIdleCallback)(e)}}}),[d]),[p,d]};var a=t(67294),s=t(98391),i="undefined"!==typeof IntersectionObserver;var c=new Map},33138:function(e,n,t){"use strict";t.d(n,{h:function(){return s},d:function(){return i}});var r=t(9669),a=t.n(r),s=a().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/rest/"}),i=a().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/gcs/helpinhand-318217.appspot.com/"})},50851:function(e,n,t){"use strict";t.d(n,{Z:function(){return m}});var r=t(85893),a=t(7623),s=t.n(a),i=t(41664),c=t(7569),o=t(67294),l=t(36808),u=t.n(l),d=t(89583),f=t(60155),p=t(63750),h=t(96245);function m(){var e=(0,o.useContext)(c.V).handleLogout,n=(0,h.Z)(u().getJSON("token")),t=n.role,a=window.location.pathname.replace("/","");return(0,r.jsx)("div",{className:s().Container,children:(0,r.jsx)("div",{className:s().NavbarItems,children:(0,r.jsxs)("div",{className:s().Navbar,children:[(0,r.jsx)(i.default,{href:"/",children:(0,r.jsx)("h1",{className:s().title,children:"HxP"})}),(0,r.jsx)(i.default,{href:"/",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(f.wB6,{}),(0,r.jsx)("a",{className:s().linkname,children:" In\xedcio"})]})})}),"USER"==t?(0,r.jsx)(i.default,{href:"/".concat(n.iss),children:(0,r.jsx)("div",{className:a=="".concat(n)?"".concat(s().linkactive):"".concat(s().topics),children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(f.eTQ,{}),(0,r.jsx)("a",{className:s().linkname,children:" Perfil"})]})})}):null,(0,r.jsx)(i.default,{href:"/home",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(d.uQe,{}),(0,r.jsx)("a",{className:s().linkname,children:" Explorar"})]})})}),(0,r.jsx)(i.default,{href:"/organizations",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(p.rVC,{}),(0,r.jsx)("a",{className:s().linkname,children:" Organiza\xe7\xf5es"})]})})}),(0,r.jsx)(i.default,{href:"/createActivity",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(f.JEU,{}),(0,r.jsx)("a",{className:s().linkname,children:" Criar Atividade"})]})})}),(0,r.jsx)(i.default,{href:"/rankings",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(f.ndN,{}),(0,r.jsx)("a",{className:s().linkname,children:" Rankings"})]})})}),"BO"==t||"ADMIN"==t?(0,r.jsxs)("div",{children:[(0,r.jsx)(i.default,{href:"/global-users",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Utilizadores "})})})}),(0,r.jsx)(i.default,{href:"/create-org",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Criar Org"})})})})]}):null,"ADMIN"==t?(0,r.jsxs)("div",{children:[(0,r.jsx)(i.default,{href:"/create-bo",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Criar Utilizador"})})})}),(0,r.jsx)(i.default,{href:"/bo-users",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Staff"})})})})]}):null,(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(f.p$f,{}),(0,r.jsx)("a",{onClick:function(){window.location.href="/",u().remove("token"),e()},className:s().linkname,children:" Logout"})]})})]})})})}},7569:function(e,n,t){"use strict";t.d(n,{V:function(){return p},H:function(){return h}});var r=t(809),a=t.n(r),s=t(85893),i=t(92447),c=t(67294),o=t(33138),l=t(11163),u=t(36808),d=t.n(u),f=t(96245),p=(0,c.createContext)({});function h(e){var n=e.children,t=(0,l.useRouter)(),r=(0,c.useState)(!1),u=r[0],h=r[1],m=(0,c.useState)(!0),v=m[0],x=m[1],j=(0,c.useState)(!1),y=j[0],g=j[1],k=(0,c.useState)(!1),N=k[0],w=k[1],b=(0,c.useState)([]),_=b[0],C=b[1],E=(0,c.useState)(""),O=E[0],S=E[1];function I(){return(I=(0,i.Z)(a().mark((function e(){return a().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:h(!1);case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function R(){return(R=(0,i.Z)(a().mark((function e(n){return a().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,o.h.post("/authentication/login",n).then((function(e){if(e.data){d().set("token",JSON.stringify(e.data),{expires:1}),h(!0);(0,f.Z)(e.data)}})).catch((function(e){S(e.response.data),h(!1)}));case 2:t.push("/home");case 3:case"end":return e.stop()}}),e)})))).apply(this,arguments)}return(0,c.useEffect)((function(){var e=d().get("token");h(!!e),x(!1)}),[]),v?(0,s.jsx)(s.Fragment,{children:" "}):(0,s.jsx)(p.Provider,{value:{authenticated:u,handleLogin:function(e){return R.apply(this,arguments)},handleLogout:function(){return I.apply(this,arguments)},subAtivity:y,setSubAtivity:g,subEdit:N,setSubEdit:w,keywords:_,setKeywords:C,error:O,setError:S},children:n})}},71013:function(e,n,t){"use strict";t.r(n),t.d(n,{default:function(){return j}});var r=t(809),a=t.n(r),s=t(85893),i=t(92447),c=t(36808),o=t.n(c),l=t(33138),u=t(96245),d=t(28647),f=t.n(d),p=t(50851),h=null;try{h=(0,u.Z)(o().getJSON("token"))}catch(y){}var m={headers:{Authorization:"Bearer "+o().getJSON("token"),"Content-Type":"application/json"}};function v(){return(v=(0,i.Z)(a().mark((function e(n){return a().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l.h.post(n,h.iss,m).then((function(e){return window.location.href=e.data})).catch((function(e){return console.log(e)}));case 2:case"end":return e.stop()}}),e)})))).apply(this,arguments)}var x=function(){return(0,s.jsxs)("div",{className:f().container,children:[(0,s.jsx)("div",{className:f().header,children:(0,s.jsx)(p.Z,{})}),(0,s.jsxs)("div",{className:f().certificate,children:[(0,s.jsxs)("div",{children:[(0,s.jsx)("img",{className:f().image,src:"https://cdn.pixabay.com/photo/2012/04/02/17/10/certificate-24960_960_720.png"}),(0,s.jsxs)("div",{className:"description",children:[(0,s.jsx)("h3",{children:"Certificado"}),(0,s.jsx)("h5",{children:"2.99\u20ac"})]})]}),(0,s.jsx)("button",{type:"submit",onClick:function(){return function(e){return v.apply(this,arguments)}("create-checkout-session")},children:"Checkout"})]})]})};function j(){return(0,s.jsx)(x,{})}},76377:function(e,n,t){(window.__NEXT_P=window.__NEXT_P||[]).push(["/checkout",function(){return t(71013)}])},7623:function(e){e.exports={NavbarItems:"styles_NavbarItems__1mok6",title:"styles_title__3zrvO",Navbar:"styles_Navbar__20cRx",logout:"styles_logout__2F-RO",topics:"styles_topics__3HcYs",links:"styles_links__WJ5oK",linkname:"styles_linkname__YKF9m"}},28647:function(e){e.exports={container:"payment_container__2j5aO",header:"payment_header__q8z-1",certificate:"payment_certificate__hXZrd",image:"payment_image__3d8yi"}},41664:function(e,n,t){e.exports=t(16071)},11163:function(e,n,t){e.exports=t(72441)},44405:function(e,n,t){"use strict";t.d(n,{w_:function(){return l}});var r=t(67294),a={color:void 0,size:void 0,className:void 0,style:void 0,attr:void 0},s=r.createContext&&r.createContext(a),i=function(){return(i=Object.assign||function(e){for(var n,t=1,r=arguments.length;t<r;t++)for(var a in n=arguments[t])Object.prototype.hasOwnProperty.call(n,a)&&(e[a]=n[a]);return e}).apply(this,arguments)},c=function(e,n){var t={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&n.indexOf(r)<0&&(t[r]=e[r]);if(null!=e&&"function"===typeof Object.getOwnPropertySymbols){var a=0;for(r=Object.getOwnPropertySymbols(e);a<r.length;a++)n.indexOf(r[a])<0&&Object.prototype.propertyIsEnumerable.call(e,r[a])&&(t[r[a]]=e[r[a]])}return t};function o(e){return e&&e.map((function(e,n){return r.createElement(e.tag,i({key:n},e.attr),o(e.child))}))}function l(e){return function(n){return r.createElement(u,i({attr:i({},e.attr)},n),o(e.child))}}function u(e){var n=function(n){var t,a=e.attr,s=e.size,o=e.title,l=c(e,["attr","size","title"]),u=s||n.size||"1em";return n.className&&(t=n.className),e.className&&(t=(t?t+" ":"")+e.className),r.createElement("svg",i({stroke:"currentColor",fill:"currentColor",strokeWidth:"0"},n.attr,a,l,{className:t,style:i(i({color:e.color||n.color},n.style),e.style),height:u,width:u,xmlns:"http://www.w3.org/2000/svg"}),o&&r.createElement("title",null,o),e.children)};return void 0!==s?r.createElement(s.Consumer,null,(function(e){return n(e)})):n(a)}},14453:function(){}},function(e){e.O(0,[9774,5445,260,2013,7925,3433,7321],(function(){return n=76377,e(e.s=n);var n}));var n=e.O();_N_E=n}]);