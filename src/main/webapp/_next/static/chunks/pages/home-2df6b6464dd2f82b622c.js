(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[229,179],{95318:function(e){e.exports=function(e){return e&&e.__esModule?e:{default:e}},e.exports.default=e.exports,e.exports.__esModule=!0},20862:function(e,t,n){var r=n(50008).default;function i(e){if("function"!==typeof WeakMap)return null;var t=new WeakMap,n=new WeakMap;return(i=function(e){return e?n:t})(e)}e.exports=function(e,t){if(!t&&e&&e.__esModule)return e;if(null===e||"object"!==r(e)&&"function"!==typeof e)return{default:e};var n=i(t);if(n&&n.has(e))return n.get(e);var s={},o=Object.defineProperty&&Object.getOwnPropertyDescriptor;for(var a in e)if("default"!==a&&Object.prototype.hasOwnProperty.call(e,a)){var l=o?Object.getOwnPropertyDescriptor(e,a):null;l&&(l.get||l.set)?Object.defineProperty(s,a,l):s[a]=e[a]}return s.default=e,n&&n.set(e,s),s},e.exports.default=e.exports,e.exports.__esModule=!0},50008:function(e){function t(n){return"function"===typeof Symbol&&"symbol"===typeof Symbol.iterator?(e.exports=t=function(e){return typeof e},e.exports.default=e.exports,e.exports.__esModule=!0):(e.exports=t=function(e){return e&&"function"===typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},e.exports.default=e.exports,e.exports.__esModule=!0),t(n)}e.exports=t,e.exports.default=e.exports,e.exports.__esModule=!0},58731:function(e,t,n){"use strict";n.r(t),n.d(t,{capitalize:function(){return r.Z},createChainedFunction:function(){return i.Z},createSvgIcon:function(){return s.Z},debounce:function(){return o.Z},deprecatedPropType:function(){return a},isMuiElement:function(){return l.Z},ownerDocument:function(){return c.Z},ownerWindow:function(){return u.Z},requirePropFactory:function(){return d},setRef:function(){return h.Z},unstable_useId:function(){return x},unsupportedProp:function(){return p},useControlled:function(){return f.Z},useEventCallback:function(){return m.Z},useForkRef:function(){return v.Z},useIsFocusVisible:function(){return y.Z}});var r=n(93871),i=n(82568),s=n(25209),o=n(79437);function a(e,t){return function(){return null}}var l=n(83711),c=n(30626),u=n(80713);function d(e){return function(){return null}}var h=n(34236);function p(e,t,n,r,i){return null}var f=n(22775),m=n(55192),v=n(17294),g=n(67294);function x(e){var t=g.useState(e),n=t[0],r=t[1],i=e||n;return g.useEffect((function(){null==n&&r("mui-".concat(Math.round(1e5*Math.random())))}),[n]),i}var y=n(24896)},88995:function(e,t,n){"use strict";var r=n(95318),i=n(20862);t.Z=void 0;var s=i(n(67294)),o=(0,r(n(2108)).default)(s.createElement("path",{d:"M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"}),"Search");t.Z=o},2108:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),Object.defineProperty(t,"default",{enumerable:!0,get:function(){return r.createSvgIcon}});var r=n(58731)},26255:function(e,t,n){"use strict";var r=n(53848),i=n(83115),s=n(62426);t.default=function(e){var t=e.src,n=e.sizes,i=e.unoptimized,s=void 0!==i&&i,a=e.priority,d=void 0!==a&&a,p=e.loading,f=e.className,m=e.quality,v=e.width,g=e.height,x=e.objectFit,y=e.objectPosition,w=e.loader,S=void 0===w?j:w,N=(0,o.default)(e,["src","sizes","unoptimized","priority","loading","className","quality","width","height","objectFit","objectPosition","loader"]),k=n?"responsive":"intrinsic",E=!1;"unsized"in N?(E=Boolean(N.unsized),delete N.unsized):"layout"in N&&(N.layout&&(k=N.layout),delete N.layout);0;var O=!d&&("lazy"===p||"undefined"===typeof p);t&&t.startsWith("data:")&&(s=!0,O=!1);var T,A,L,D=(0,h.useIntersection)({rootMargin:"200px",disabled:!O}),M=r(D,2),P=M[0],z=M[1],C=!O||z,Z=b(v),R=b(g),Y=b(m),F={position:"absolute",top:0,left:0,bottom:0,right:0,boxSizing:"border-box",padding:0,border:"none",margin:"auto",display:"block",width:0,height:0,minWidth:"100%",maxWidth:"100%",minHeight:"100%",maxHeight:"100%",objectFit:x,objectPosition:y};if("undefined"!==typeof Z&&"undefined"!==typeof R&&"fill"!==k){var B=R/Z,I=isNaN(B)?"100%":"".concat(100*B,"%");"responsive"===k?(T={display:"block",overflow:"hidden",position:"relative",boxSizing:"border-box",margin:0},A={display:"block",boxSizing:"border-box",paddingTop:I}):"intrinsic"===k?(T={display:"inline-block",maxWidth:"100%",overflow:"hidden",position:"relative",boxSizing:"border-box",margin:0},A={boxSizing:"border-box",display:"block",maxWidth:"100%"},L='<svg width="'.concat(Z,'" height="').concat(R,'" xmlns="http://www.w3.org/2000/svg" version="1.1"/>')):"fixed"===k&&(T={overflow:"hidden",boxSizing:"border-box",display:"inline-block",position:"relative",width:Z,height:R})}else"undefined"===typeof Z&&"undefined"===typeof R&&"fill"===k&&(T={display:"block",overflow:"hidden",position:"absolute",top:0,left:0,bottom:0,right:0,boxSizing:"border-box",margin:0});var H={src:"data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",srcSet:void 0,sizes:void 0};C&&(H=_({src:t,unoptimized:s,layout:k,width:Z,quality:Y,sizes:n,loader:S}));E&&(T=void 0,A=void 0,F=void 0);return l.default.createElement("div",{style:T},A?l.default.createElement("div",{style:A},L?l.default.createElement("img",{style:{maxWidth:"100%",display:"block",margin:0,border:"none",padding:0},alt:"","aria-hidden":!0,role:"presentation",src:"data:image/svg+xml;base64,".concat((0,u.toBase64)(L))}):null):null,!C&&l.default.createElement("noscript",null,l.default.createElement("img",Object.assign({},N,_({src:t,unoptimized:s,layout:k,width:Z,quality:Y,sizes:n,loader:S}),{src:t,decoding:"async",sizes:n,style:F,className:f}))),l.default.createElement("img",Object.assign({},N,H,{decoding:"async",className:f,ref:P,style:F})),d?l.default.createElement(c.default,null,l.default.createElement("link",{key:"__nimg-"+H.src+H.srcSet+H.sizes,rel:"preload",as:"image",href:H.srcSet?void 0:H.src,imagesrcset:H.srcSet,imagesizes:H.sizes})):null)};var o=s(n(26169)),a=s(n(9566)),l=s(n(67294)),c=s(n(57947)),u=n(47239),d=n(5655),h=n(75749);var p=new Map([["imgix",function(e){var t=e.root,n=e.src,r=e.width,i=e.quality,s=["auto=format","fit=max","w="+r],o="";i&&s.push("q="+i);s.length&&(o="?"+s.join("&"));return"".concat(t).concat(w(n)).concat(o)}],["cloudinary",function(e){var t=e.root,n=e.src,r=e.width,i=e.quality,s=["f_auto","c_limit","w_"+r,"q_"+(i||"auto")].join(",")+"/";return"".concat(t).concat(s).concat(w(n))}],["akamai",function(e){var t=e.root,n=e.src,r=e.width;return"".concat(t).concat(w(n),"?imwidth=").concat(r)}],["default",function(e){var t=e.root,n=e.src,r=e.width,i=e.quality;0;return"".concat(t,"?url=").concat(encodeURIComponent(n),"&w=").concat(r,"&q=").concat(i||75)}]]),f={deviceSizes:[640,750,828,1080,1200,1920,2048,3840],imageSizes:[16,32,48,64,96,128,256,384],path:"",loader:"imgix"}||d.imageConfigDefault,m=f.deviceSizes,v=f.imageSizes,g=f.loader,x=f.path,y=(f.domains,[].concat(i(m),i(v)));function _(e){var t=e.src,n=e.unoptimized,r=e.layout,s=e.width,o=e.quality,a=e.sizes,l=e.loader;if(n)return{src:t,srcSet:void 0,sizes:void 0};var c=function(e,t,n){if(n&&("fill"===t||"responsive"===t)){var r=i(n.matchAll(/(^|\s)(1?\d?\d)vw/g)).map((function(e){return parseInt(e[2])}));if(r.length){var s=.01*Math.min.apply(Math,i(r));return{widths:y.filter((function(e){return e>=m[0]*s})),kind:"w"}}return{widths:y,kind:"w"}}return"number"!==typeof e||"fill"===t||"responsive"===t?{widths:m,kind:"w"}:{widths:i(new Set([e,2*e].map((function(e){return y.find((function(t){return t>=e}))||y[y.length-1]})))),kind:"x"}}(s,r,a),u=c.widths,d=c.kind,h=u.length-1;return{sizes:a||"w"!==d?a:"100vw",srcSet:u.map((function(e,n){return"".concat(l({src:t,quality:o,width:e})," ").concat("w"===d?e:n+1).concat(d)})).join(", "),src:l({src:t,quality:o,width:u[h]})}}function b(e){return"number"===typeof e?e:"string"===typeof e?parseInt(e,10):void 0}function j(e){var t=p.get(g);if(t)return t((0,a.default)({root:x},e));throw new Error('Unknown "loader" found in "next.config.js". Expected: '.concat(d.VALID_LOADERS.join(", "),". Received: ").concat(g))}function w(e){return"/"===e[0]?e.slice(1):e}m.sort((function(e,t){return e-t})),y.sort((function(e,t){return e-t}))},47239:function(e,t){"use strict";t.__esModule=!0,t.toBase64=function(e){return window.btoa(e)}},33138:function(e,t,n){"use strict";n.d(t,{h:function(){return s},d:function(){return o}});var r=n(9669),i=n.n(r),s=i().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/rest/"}),o=i().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/gcs/helpinhand-318217.appspot.com/"})},50851:function(e,t,n){"use strict";n.d(t,{Z:function(){return m}});var r=n(85893),i=n(7623),s=n.n(i),o=n(41664),a=n(7569),l=n(67294),c=n(36808),u=n.n(c),d=n(89583),h=n(60155),p=n(63750),f=n(96245);function m(){var e=(0,l.useContext)(a.V).handleLogout,t=(0,f.Z)(u().getJSON("token")),n=t.role,i=window.location.pathname.replace("/","");return(0,r.jsx)("div",{className:s().Container,children:(0,r.jsx)("div",{className:s().NavbarItems,children:(0,r.jsxs)("div",{className:s().Navbar,children:[(0,r.jsx)(o.default,{href:"/",children:(0,r.jsx)("h1",{className:s().title,children:"HxP"})}),(0,r.jsx)(o.default,{href:"/",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(h.wB6,{}),(0,r.jsx)("a",{className:s().linkname,children:" In\xedcio"})]})})}),"USER"==n?(0,r.jsx)(o.default,{href:"/".concat(t.iss),children:(0,r.jsx)("div",{className:i=="".concat(t)?"".concat(s().linkactive):"".concat(s().topics),children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(h.eTQ,{}),(0,r.jsx)("a",{className:s().linkname,children:" Perfil"})]})})}):null,(0,r.jsx)(o.default,{href:"/home",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(d.uQe,{}),(0,r.jsx)("a",{className:s().linkname,children:" Explorar"})]})})}),(0,r.jsx)(o.default,{href:"/organizations",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(p.rVC,{}),(0,r.jsx)("a",{className:s().linkname,children:" Organiza\xe7\xf5es"})]})})}),(0,r.jsx)(o.default,{href:"/createActivity",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(h.JEU,{}),(0,r.jsx)("a",{className:s().linkname,children:" Criar Atividade"})]})})}),(0,r.jsx)(o.default,{href:"/rankings",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(h.ndN,{}),(0,r.jsx)("a",{className:s().linkname,children:" Rankings"})]})})}),"BO"==n||"ADMIN"==n?(0,r.jsxs)("div",{children:[(0,r.jsx)(o.default,{href:"/global-users",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Utilizadores "})})})}),(0,r.jsx)(o.default,{href:"/create-org",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Criar Org"})})})})]}):null,"ADMIN"==n?(0,r.jsxs)("div",{children:[(0,r.jsx)(o.default,{href:"/create-bo",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Criar Utilizador"})})})}),(0,r.jsx)(o.default,{href:"/bo-users",children:(0,r.jsx)("div",{className:s().topics,children:(0,r.jsx)("span",{className:s().links,children:(0,r.jsx)("a",{className:s().linkname,children:" Staff"})})})})]}):null,(0,r.jsx)("div",{className:s().topics,children:(0,r.jsxs)("span",{className:s().links,children:[(0,r.jsx)(h.p$f,{}),(0,r.jsx)("a",{onClick:function(){window.location.href="/",u().remove("token"),e()},className:s().linkname,children:" Logout"})]})})]})})})}},39547:function(e,t,n){"use strict";n.d(t,{Z:function(){return l}});var r=n(85893),i=n(9008),s=n(41664),o=n(67715),a=n.n(o);function l(){return(0,r.jsxs)("div",{className:a().container,children:[(0,r.jsx)(i.default,{children:(0,r.jsx)("title",{children:"Acesso Proibido"})}),(0,r.jsx)("h1",{children:"Acesso Proibido"}),(0,r.jsx)(s.default,{href:"/",children:(0,r.jsx)("a",{className:a().link,children:"Regressar \xe0 p\xe1gina inicial"})})]})}},7569:function(e,t,n){"use strict";n.d(t,{V:function(){return p},H:function(){return f}});var r=n(809),i=n.n(r),s=n(85893),o=n(92447),a=n(67294),l=n(33138),c=n(11163),u=n(36808),d=n.n(u),h=n(96245),p=(0,a.createContext)({});function f(e){var t=e.children,n=(0,c.useRouter)(),r=(0,a.useState)(!1),u=r[0],f=r[1],m=(0,a.useState)(!0),v=m[0],g=m[1],x=(0,a.useState)(!1),y=x[0],_=x[1],b=(0,a.useState)(!1),j=b[0],w=b[1],S=(0,a.useState)([]),N=S[0],k=S[1],E=(0,a.useState)(""),O=E[0],T=E[1];function A(){return(A=(0,o.Z)(i().mark((function e(){return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:f(!1);case 1:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function L(){return(L=(0,o.Z)(i().mark((function e(t){return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l.h.post("/authentication/login",t).then((function(e){if(e.data){d().set("token",JSON.stringify(e.data),{expires:1}),f(!0);(0,h.Z)(e.data)}})).catch((function(e){T(e.response.data),f(!1)}));case 2:n.push("/home");case 3:case"end":return e.stop()}}),e)})))).apply(this,arguments)}return(0,a.useEffect)((function(){var e=d().get("token");f(!!e),g(!1)}),[]),v?(0,s.jsx)(s.Fragment,{children:" "}):(0,s.jsx)(p.Provider,{value:{authenticated:u,handleLogin:function(e){return L.apply(this,arguments)},handleLogout:function(){return A.apply(this,arguments)},subAtivity:y,setSubAtivity:_,subEdit:j,setSubEdit:w,keywords:N,setKeywords:k,error:O,setError:T},children:t})}},23710:function(e,t,n){"use strict";n.r(t),n.d(t,{default:function(){return A}});var r=n(67294),i=n(85893),s=n(809),o=n.n(s),a=n(92447),l=n(26265),c=n(15665),u=n.n(c),d=n(9008),h=n(50851),p=n(36808),f=n.n(p),m=n(33138),v=n(88995),g=n(55558),x=n.n(g),y=n(25675),_=n(41664),b=n(35723),j=n(22605);function w(e){return S.apply(this,arguments)}function S(){return(S=(0,a.Z)(o().mark((function e(t){var n,r;return o().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return n=f().getJSON("token"),r={headers:{Authorization:"Bearer "+n}},e.next=4,m.h.get(t,r).then((function(e){return e.data})).catch((function(e){console.log("")}));case 4:return e.abrupt("return",e.sent);case 5:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function N(e){var t=(0,b.ZP)("users/get/".concat(e.activityOwner),w),n=t.data,r=(t.error,n);if(!n)return(0,i.jsx)(i.Fragment,{});return(0,i.jsxs)("div",{className:x().container,children:[(0,i.jsxs)("div",{className:x().avatarandname,children:[(0,i.jsx)(_.default,{href:"".concat(r.username),children:(0,i.jsx)("div",{className:x().userimage,children:(0,i.jsx)(y.default,{loader:function(){return"https://storage.googleapis.com/helpinhand-318217.appspot.com/".concat(r.image)},src:"me.png",placeholder:"blur",width:70,height:70,className:x().image})})}),(0,i.jsxs)("div",{className:x().username,children:[(0,i.jsx)(_.default,{href:"".concat(r.username),children:(0,i.jsx)("p",{className:x().name,children:r.name})}),(0,i.jsx)(_.default,{href:"".concat(r.username),children:(0,i.jsxs)("p",{className:x().arroba,children:["  @",r.username]})})]})]}),(0,i.jsxs)("div",{className:x().activity,children:[(0,i.jsx)("h3",{children:e.title}),(0,i.jsxs)("div",{className:x().activityinfo,children:[(0,i.jsxs)("div",{className:x().localdate,children:[(0,i.jsx)("p",{children:(0,j.Z)(new Date(e.date),"dd/MM/yyyy")}),(0,i.jsx)("p",{children:e.location})]}),(0,i.jsxs)("div",{className:x().participants,children:[(0,i.jsx)("h4",{children:"Participantes"}),(0,i.jsxs)("p",{children:[e.participants,"/",e.totalParticipants]})]})]})]}),(0,i.jsxs)("div",{className:x().vermaiscontainer,children:[(0,i.jsx)(_.default,{href:"activity/".concat(e.activityOwner,"/").concat(e.ID),children:(0,i.jsx)("p",{children:"Ver mais"})}),(0,i.jsx)("div",{className:x().vermais})]})]})}var k=n(58533),E=n(39547);function O(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function T(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?O(Object(n),!0).forEach((function(t){(0,l.Z)(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):O(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function A(){var e=f().getJSON("token");if(!e)return(0,i.jsx)(E.Z,{});var t=(0,r.useState)([]),n=t[0],s=t[1],l=(0,r.useState)(null),c=l[0],p=l[1],g=(0,r.useState)(!0),x=g[0],y=g[1],_=(0,r.useState)(!1),b=(_[0],_[1],{headers:{Authorization:"Bearer "+e,"Content-Type":"application/json"}}),j=(0,r.useState)({checkedA:!0}),w=(j[0],j[1],function(){var e=(0,a.Z)(o().mark((function e(t){return o().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if("Enter"!==t.key){e.next=4;break}return console.log("enter"),e.next=4,m.h.get("activities/search/?keyword=".concat(A),b).then((function(e){console.log(e.data),y(!1),s(e.data)}));case 4:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()),S=function(){var e=(0,a.Z)(o().mark((function e(){return o().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,m.h.get("activities/search/?keyword=".concat(A),b).then((function(e){console.log(e.data),y(!1),s(e.data)}));case 2:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),O=(0,r.useState)(""),A=O[0],L=O[1];function D(){console.log("segundo fetch"),m.h.post("activities/listCursor",c,b).then((function(e){0!==e.data.results.length?(console.log(e.data.results),s((function(t){return t.concat(e.data.results)})),p(e.data.cursorString),console.log(c)):y(!1)}))}return(0,r.useEffect)((function(){D()}),[c]),(0,i.jsxs)("div",{className:u().container,children:[(0,i.jsx)(d.default,{children:(0,i.jsx)("title",{children:"Home"})}),(0,i.jsx)("div",{className:u().header,children:(0,i.jsx)(h.Z,{})}),(0,i.jsxs)("div",{id:"target",className:u().Feed,children:[(0,i.jsxs)("div",{className:u().searchBar,children:[(0,i.jsx)("button",{children:(0,i.jsx)(v.Z,{fontSize:"large",onClick:S,onKeyDown:function(){return w}})}),(0,i.jsx)("input",{className:u().formP,name:"pesquisa",placeholder:"Pesquisa por atividades",onChange:function(e){return function(e){L(e.target.value),""!==e.target.value&&null!==e.target.value||(y(!0),s([]),p(null))}(e)}})]}),(0,i.jsx)("div",{className:u().scroll,children:(0,i.jsx)(k.Z,{dataLength:5*n.length,next:D,hasMore:x,loader:(0,i.jsx)("h4",{children:"Loading..."}),endMessage:(0,i.jsxs)("div",{children:[(0,i.jsx)("br",{}),(0,i.jsx)("p",{style:{textAlign:"center"},children:(0,i.jsx)("b",{children:"N\xe3o h\xe1 mais atividades."})})]}),children:n?n.map((function(e,t){return(0,r.createElement)(N,T(T({},e),{},{key:t}))})):(0,i.jsx)(i.Fragment,{})})})]}),(0,i.jsx)("div",{className:u().other})]})}},91344:function(e,t,n){(window.__NEXT_P=window.__NEXT_P||[]).push(["/home",function(){return n(23710)}])},55558:function(e){e.exports={container:"styles_container__2X-P5",avatarandname:"styles_avatarandname__1etW-",userimage:"styles_userimage__1mG5Q",image:"styles_image__prNSZ",username:"styles_username__3U9Ig",name:"styles_name__2KULe",arroba:"styles_arroba__3Z_fm",activity:"styles_activity__3upyM",activityinfo:"styles_activityinfo__3l_Xi",localdate:"styles_localdate__3HTz0",participants:"styles_participants__19iSN",vermaiscontainer:"styles_vermaiscontainer__22Zkw"}},7623:function(e){e.exports={NavbarItems:"styles_NavbarItems__1mok6",title:"styles_title__3zrvO",Navbar:"styles_Navbar__20cRx",logout:"styles_logout__2F-RO",topics:"styles_topics__3HcYs",links:"styles_links__WJ5oK",linkname:"styles_linkname__YKF9m"}},67715:function(e){e.exports={container:"styles_container__3h9vA",link:"styles_link__3e4fM"}},15665:function(e){e.exports={container:"base_container__3b-bk",header:"base_header__1vI13",Feed:"base_Feed__1i9Wq",scroll:"base_scroll__1hCko",searchBar:"base_searchBar__1ng96",activity:"base_activity__1pnCS",formP:"base_formP__2lKGF",other:"base_other__npAFr",filters:"base_filters__3ShyN",banneravatar:"base_banneravatar__2H-c0"}},5655:function(e,t){"use strict";t.__esModule=!0,t.imageConfigDefault=t.VALID_LOADERS=void 0;t.VALID_LOADERS=["default","imgix","cloudinary","akamai"];t.imageConfigDefault={deviceSizes:[640,750,828,1080,1200,1920,2048,3840],imageSizes:[16,32,48,64,96,128,256,384],path:"/_next/image",loader:"default",domains:[]}},25675:function(e,t,n){e.exports=n(26255)},9566:function(e){function t(){return e.exports=t=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e},t.apply(this,arguments)}e.exports=t},26169:function(e){e.exports=function(e,t){if(null==e)return{};var n,r,i={},s=Object.keys(e);for(r=0;r<s.length;r++)n=s[r],t.indexOf(n)>=0||(i[n]=e[n]);return i}},58533:function(e,t,n){"use strict";var r=n(67294),i=function(e,t){return(i=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])})(e,t)};var s=function(){return(s=Object.assign||function(e){for(var t,n=1,r=arguments.length;n<r;n++)for(var i in t=arguments[n])Object.prototype.hasOwnProperty.call(t,i)&&(e[i]=t[i]);return e}).apply(this,arguments)};var o="Pixel",a="Percent",l={unit:a,value:.8};function c(e){return"number"===typeof e?{unit:a,value:100*e}:"string"===typeof e?e.match(/^(\d*(\.\d+)?)px$/)?{unit:o,value:parseFloat(e)}:e.match(/^(\d*(\.\d+)?)%$/)?{unit:a,value:parseFloat(e)}:(console.warn('scrollThreshold format is invalid. Valid formats: "120px", "50%"...'),l):(console.warn("scrollThreshold should be string or number"),l)}var u=function(e){function t(t){var n=e.call(this,t)||this;return n.lastScrollTop=0,n.actionTriggered=!1,n.startY=0,n.currentY=0,n.dragging=!1,n.maxPullDownDistance=0,n.getScrollableTarget=function(){return n.props.scrollableTarget instanceof HTMLElement?n.props.scrollableTarget:"string"===typeof n.props.scrollableTarget?document.getElementById(n.props.scrollableTarget):(null===n.props.scrollableTarget&&console.warn("You are trying to pass scrollableTarget but it is null. This might\n        happen because the element may not have been added to DOM yet.\n        See https://github.com/ankeetmaini/react-infinite-scroll-component/issues/59 for more info.\n      "),null)},n.onStart=function(e){n.lastScrollTop||(n.dragging=!0,e instanceof MouseEvent?n.startY=e.pageY:e instanceof TouchEvent&&(n.startY=e.touches[0].pageY),n.currentY=n.startY,n._infScroll&&(n._infScroll.style.willChange="transform",n._infScroll.style.transition="transform 0.2s cubic-bezier(0,0,0.31,1)"))},n.onMove=function(e){n.dragging&&(e instanceof MouseEvent?n.currentY=e.pageY:e instanceof TouchEvent&&(n.currentY=e.touches[0].pageY),n.currentY<n.startY||(n.currentY-n.startY>=Number(n.props.pullDownToRefreshThreshold)&&n.setState({pullToRefreshThresholdBreached:!0}),n.currentY-n.startY>1.5*n.maxPullDownDistance||n._infScroll&&(n._infScroll.style.overflow="visible",n._infScroll.style.transform="translate3d(0px, "+(n.currentY-n.startY)+"px, 0px)")))},n.onEnd=function(){n.startY=0,n.currentY=0,n.dragging=!1,n.state.pullToRefreshThresholdBreached&&(n.props.refreshFunction&&n.props.refreshFunction(),n.setState({pullToRefreshThresholdBreached:!1})),requestAnimationFrame((function(){n._infScroll&&(n._infScroll.style.overflow="auto",n._infScroll.style.transform="none",n._infScroll.style.willChange="unset")}))},n.onScrollListener=function(e){"function"===typeof n.props.onScroll&&setTimeout((function(){return n.props.onScroll&&n.props.onScroll(e)}),0);var t=n.props.height||n._scrollableNode?e.target:document.documentElement.scrollTop?document.documentElement:document.body;n.actionTriggered||((n.props.inverse?n.isElementAtTop(t,n.props.scrollThreshold):n.isElementAtBottom(t,n.props.scrollThreshold))&&n.props.hasMore&&(n.actionTriggered=!0,n.setState({showLoader:!0}),n.props.next&&n.props.next()),n.lastScrollTop=t.scrollTop)},n.state={showLoader:!1,pullToRefreshThresholdBreached:!1,prevDataLength:t.dataLength},n.throttledOnScrollListener=function(e,t,n,r){var i,s=!1,o=0;function a(){i&&clearTimeout(i)}function l(){var l=this,c=Date.now()-o,u=arguments;function d(){o=Date.now(),n.apply(l,u)}function h(){i=void 0}s||(r&&!i&&d(),a(),void 0===r&&c>e?d():!0!==t&&(i=setTimeout(r?h:d,void 0===r?e-c:e)))}return"boolean"!==typeof t&&(r=n,n=t,t=void 0),l.cancel=function(){a(),s=!0},l}(150,n.onScrollListener).bind(n),n.onStart=n.onStart.bind(n),n.onMove=n.onMove.bind(n),n.onEnd=n.onEnd.bind(n),n}return function(e,t){function n(){this.constructor=e}i(e,t),e.prototype=null===t?Object.create(t):(n.prototype=t.prototype,new n)}(t,e),t.prototype.componentDidMount=function(){if("undefined"===typeof this.props.dataLength)throw new Error('mandatory prop "dataLength" is missing. The prop is needed when loading more content. Check README.md for usage');if(this._scrollableNode=this.getScrollableTarget(),this.el=this.props.height?this._infScroll:this._scrollableNode||window,this.el&&this.el.addEventListener("scroll",this.throttledOnScrollListener),"number"===typeof this.props.initialScrollY&&this.el&&this.el instanceof HTMLElement&&this.el.scrollHeight>this.props.initialScrollY&&this.el.scrollTo(0,this.props.initialScrollY),this.props.pullDownToRefresh&&this.el&&(this.el.addEventListener("touchstart",this.onStart),this.el.addEventListener("touchmove",this.onMove),this.el.addEventListener("touchend",this.onEnd),this.el.addEventListener("mousedown",this.onStart),this.el.addEventListener("mousemove",this.onMove),this.el.addEventListener("mouseup",this.onEnd),this.maxPullDownDistance=this._pullDown&&this._pullDown.firstChild&&this._pullDown.firstChild.getBoundingClientRect().height||0,this.forceUpdate(),"function"!==typeof this.props.refreshFunction))throw new Error('Mandatory prop "refreshFunction" missing.\n          Pull Down To Refresh functionality will not work\n          as expected. Check README.md for usage\'')},t.prototype.componentWillUnmount=function(){this.el&&(this.el.removeEventListener("scroll",this.throttledOnScrollListener),this.props.pullDownToRefresh&&(this.el.removeEventListener("touchstart",this.onStart),this.el.removeEventListener("touchmove",this.onMove),this.el.removeEventListener("touchend",this.onEnd),this.el.removeEventListener("mousedown",this.onStart),this.el.removeEventListener("mousemove",this.onMove),this.el.removeEventListener("mouseup",this.onEnd)))},t.prototype.componentDidUpdate=function(e){this.props.dataLength!==e.dataLength&&(this.actionTriggered=!1,this.setState({showLoader:!1}))},t.getDerivedStateFromProps=function(e,t){return e.dataLength!==t.prevDataLength?s(s({},t),{prevDataLength:e.dataLength}):null},t.prototype.isElementAtTop=function(e,t){void 0===t&&(t=.8);var n=e===document.body||e===document.documentElement?window.screen.availHeight:e.clientHeight,r=c(t);return r.unit===o?e.scrollTop<=r.value+n-e.scrollHeight+1:e.scrollTop<=r.value/100+n-e.scrollHeight+1},t.prototype.isElementAtBottom=function(e,t){void 0===t&&(t=.8);var n=e===document.body||e===document.documentElement?window.screen.availHeight:e.clientHeight,r=c(t);return r.unit===o?e.scrollTop+n>=e.scrollHeight-r.value:e.scrollTop+n>=r.value/100*e.scrollHeight},t.prototype.render=function(){var e=this,t=s({height:this.props.height||"auto",overflow:"auto",WebkitOverflowScrolling:"touch"},this.props.style),n=this.props.hasChildren||!!(this.props.children&&this.props.children instanceof Array&&this.props.children.length),i=this.props.pullDownToRefresh&&this.props.height?{overflow:"auto"}:{};return r.createElement("div",{style:i,className:"infinite-scroll-component__outerdiv"},r.createElement("div",{className:"infinite-scroll-component "+(this.props.className||""),ref:function(t){return e._infScroll=t},style:t},this.props.pullDownToRefresh&&r.createElement("div",{style:{position:"relative"},ref:function(t){return e._pullDown=t}},r.createElement("div",{style:{position:"absolute",left:0,right:0,top:-1*this.maxPullDownDistance}},this.state.pullToRefreshThresholdBreached?this.props.releaseToRefreshContent:this.props.pullDownToRefreshContent)),this.props.children,!this.state.showLoader&&!n&&this.props.hasMore&&this.props.loader,this.state.showLoader&&this.props.hasMore&&this.props.loader,!this.props.hasMore&&this.props.endMessage))},t}(r.Component);t.Z=u},14453:function(){}},function(e){e.O(0,[9774,5445,260,2013,7925,3433,7321,3996,8540,7399,2605],(function(){return t=91344,e(e.s=t);var t}));var t=e.O();_N_E=t}]);