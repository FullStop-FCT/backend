(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[229,179],{9832:function(e,a,n){"use strict";var t=n(5318),r=n(862);a.Z=void 0;var i=r(n(7294)),o=(0,t(n(2108)).default)(i.createElement("path",{d:"M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z"}),"LocationOn");a.Z=o},8995:function(e,a,n){"use strict";var t=n(5318),r=n(862);a.Z=void 0;var i=r(n(7294)),o=(0,t(n(2108)).default)(i.createElement("path",{d:"M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"}),"Search");a.Z=o},3138:function(e,a,n){"use strict";n.d(a,{h:function(){return i},d:function(){return o}});var t=n(9669),r=n.n(t),i=r().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/rest/"}),o=r().create({baseURL:"https://helpinhand-318217.ey.r.appspot.com/gcs/helpinhand-318217.appspot.com/"})},7879:function(e,a,n){"use strict";n.d(a,{Z:function(){return v}});var t=n(809),r=n.n(t),i=n(5893),o=n(2447),s=n(5558),c=n.n(s),l=n(3138),d=n(5675),u=n(1664),h=n(5723),p=n(2605);function m(e){return f.apply(this,arguments)}function f(){return(f=(0,o.Z)(r().mark((function e(a){return r().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l.h.get(a).then((function(e){return e.data}));case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function v(e){var a=(0,h.ZP)("users/self/".concat(e.activityOwner),m),n=a.data,t=a.error,r=n;if(t)return(0,i.jsx)("div",{children:"error"});if(!n)return(0,i.jsx)("div",{children:"Loading"});return(0,i.jsxs)("div",{className:c().container,children:[(0,i.jsxs)("div",{className:c().avatarandname,children:[(0,i.jsx)(u.default,{href:"".concat(r.username),children:(0,i.jsx)("div",{className:c().userimage,children:(0,i.jsx)(d.default,{loader:function(){return"https://storage.googleapis.com/helpinhand-318217.appspot.com/".concat(r.image)},src:"me.png",placeholder:"blur",width:70,height:70,className:c().image})})}),(0,i.jsxs)("div",{className:c().username,children:[(0,i.jsx)(u.default,{href:"".concat(r.username),children:(0,i.jsx)("p",{className:c().name,children:r.name})}),(0,i.jsx)(u.default,{href:"".concat(r.username),children:(0,i.jsxs)("p",{className:c().arroba,children:["  @",r.username]})})]})]}),(0,i.jsxs)("div",{className:c().activity,children:[(0,i.jsx)("h3",{children:e.title}),(0,i.jsxs)("div",{className:c().activityinfo,children:[(0,i.jsxs)("div",{className:c().localdate,children:[(0,i.jsx)("p",{children:(0,p.Z)(new Date(e.date),"dd/MM/yyyy")}),(0,i.jsx)("p",{children:e.location})]}),(0,i.jsxs)("div",{className:c().participants,children:[(0,i.jsx)("h4",{children:"Participantes"}),(0,i.jsxs)("p",{children:[e.participants,"/",e.totalParticipants]})]})]})]}),(0,i.jsxs)("div",{className:c().vermaiscontainer,children:[(0,i.jsx)(u.default,{href:"activity/".concat(e.activityOwner,"/").concat(e.ID),children:(0,i.jsx)("p",{children:"Ver mais"})}),(0,i.jsx)("div",{className:c().vermais})]})]})}},851:function(e,a,n){"use strict";n.d(a,{Z:function(){return m}});var t=n(5893),r=n(7294),i=n(7623),o=n.n(i),s=n(1664),c=n(7569),l=n(6808),d=n.n(l),u=n(9583),h=n(155),p=n(3750);function m(){var e=(0,r.useState)(!1),a=(e[0],e[1],(0,r.useContext)(c.V).handleLogout),n=d().get("user");return(0,t.jsx)("div",{className:o().Container,children:(0,t.jsxs)("div",{className:o().NavbarItems,children:[(0,t.jsx)(s.default,{href:"/",children:(0,t.jsx)("h1",{children:"Helpin'Hand"})}),(0,t.jsxs)("div",{className:o().Navbar,children:[(0,t.jsx)(s.default,{href:"/",children:(0,t.jsx)("div",{className:o().topics,children:(0,t.jsxs)("span",{className:o().links,children:[(0,t.jsx)(h.wB6,{}),(0,t.jsx)("a",{className:o().linkname,children:" In\xedcio"})]})})}),(0,t.jsx)(s.default,{href:"/".concat(n),children:(0,t.jsx)("div",{className:o().topics,children:(0,t.jsxs)("span",{className:o().links,children:[(0,t.jsx)(h.eTQ,{}),(0,t.jsx)("a",{className:o().linkname,children:" Perfil"})]})})}),(0,t.jsx)(s.default,{href:"/home",children:(0,t.jsx)("div",{className:o().topics,children:(0,t.jsxs)("span",{className:o().links,children:[(0,t.jsx)(u.uQe,{}),(0,t.jsx)("a",{className:o().linkname,children:" Explorar"})]})})}),(0,t.jsx)(s.default,{href:"/organizations",children:(0,t.jsx)("div",{className:o().topics,children:(0,t.jsxs)("span",{className:o().links,children:[(0,t.jsx)(p.rVC,{}),(0,t.jsx)("a",{className:o().linkname,children:" Organiza\xe7\xf5es"})]})})}),(0,t.jsx)(s.default,{href:"/createActivity",children:(0,t.jsx)("div",{className:o().topics,children:(0,t.jsxs)("span",{className:o().links,children:[(0,t.jsx)(h.JEU,{}),(0,t.jsx)("a",{className:o().linkname,children:" Criar Atividade"})]})})}),(0,t.jsx)(s.default,{href:"/rankings",children:(0,t.jsx)("div",{className:o().topics,children:(0,t.jsxs)("span",{className:o().links,children:[(0,t.jsx)(h.ndN,{}),(0,t.jsx)("a",{className:o().linkname,children:" Rankings"})]})})}),(0,t.jsx)("button",{onClick:function(){return a()},children:"Logout"})]})]})})}},1945:function(e,a,n){"use strict";n.d(a,{Z:function(){return i}});var t=n(5893),r=n(3843);function i(){return(0,t.jsx)("div",{children:(0,t.jsx)(r.te,{})})}},3281:function(e,a,n){"use strict";n.d(a,{Z:function(){return s}});var t=n(5893),r=n(1163),i=n(6808),o=n.n(i);function s(){var e=(0,r.useRouter)();return(0,t.jsx)("div",{children:(0,t.jsxs)("div",{children:[(0,t.jsx)("h1",{children:"Your session has expired"}),(0,t.jsx)("button",{onClick:function(){o().remove("token"),o().remove("user"),e.push("/login")},children:"Login in"})]})})}},7569:function(e,a,n){"use strict";n.d(a,{V:function(){return p},H:function(){return m}});var t=n(809),r=n.n(t),i=n(5893),o=n(2447),s=n(7294),c=n(3138),l=n(1163),d=n(6808),u=n.n(d),h=n(1945),p=(0,s.createContext)({});function m(e){var a=e.children,n=(0,l.useRouter)(),t=(0,s.useState)(!1),d=t[0],m=t[1],f=(0,s.useState)(!0),v=f[0],x=f[1],y=(0,s.useState)(!1),g=y[0],j=y[1],b=(0,s.useState)(!1),_=b[0],k=b[1],N=(0,s.useState)([]),Z=N[0],S=N[1];(0,s.useEffect)((function(){var e=u().get("token");m(!!e),x(!1)}),[]);function P(){return(P=(0,o.Z)(r().mark((function e(){return r().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:m(!1),u().remove("token"),u().remove("user"),window.location.href="/";case 4:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function C(){return(C=(0,o.Z)(r().mark((function e(a){return r().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,c.h.post("authentication/login",a).then((function(e){e.data&&(console.log(e),u().set("token",JSON.stringify(e.data)),u().set("user",e.data.username),m(!0),n.push("/".concat(e.data.username)))})).catch((function(e){console.log(e),m(!1)}));case 2:case"end":return e.stop()}}),e)})))).apply(this,arguments)}return v?(0,i.jsx)(h.Z,{}):(0,i.jsx)(p.Provider,{value:{authenticated:d,handleLogin:function(e){return C.apply(this,arguments)},handleLogout:function(){return P.apply(this,arguments)},subAtivity:g,setSubAtivity:j,subEdit:_,setSubEdit:k,keywords:Z,setKeywords:S},children:a})}},3431:function(e,a,n){"use strict";n.r(a),n.d(a,{default:function(){return ee}});var t=n(809),r=n.n(t),i=n(7294),o=n(5893),s=n(6265),c=n(4121),l=n(2447),d=n(5665),u=n.n(d),h=n(9008),p=n(851),m=n(7569),f=n(6808),v=n.n(f),x=n(3138),y=n(5723),g=n(1163),j=n(3281),b=n(8995),_=n(9832),k=n(926),N=n(2949),Z=(n(5697),n(6010)),S=n(4699),P=n(2775),C=n(2601),I=n(4670),w=n(7812),E=i.forwardRef((function(e,a){var n=e.autoFocus,t=e.checked,r=e.checkedIcon,o=e.classes,s=e.className,c=e.defaultChecked,l=e.disabled,d=e.icon,u=e.id,h=e.inputProps,p=e.inputRef,m=e.name,f=e.onBlur,v=e.onChange,x=e.onFocus,y=e.readOnly,g=e.required,j=e.tabIndex,b=e.type,_=e.value,I=(0,N.Z)(e,["autoFocus","checked","checkedIcon","classes","className","defaultChecked","disabled","icon","id","inputProps","inputRef","name","onBlur","onChange","onFocus","readOnly","required","tabIndex","type","value"]),E=(0,P.Z)({controlled:t,default:Boolean(c),name:"SwitchBase",state:"checked"}),O=(0,S.Z)(E,2),B=O[0],z=O[1],L=(0,C.Z)(),F=l;L&&"undefined"===typeof F&&(F=L.disabled);var M="checkbox"===b||"radio"===b;return i.createElement(w.Z,(0,k.Z)({component:"span",className:(0,Z.Z)(o.root,s,B&&o.checked,F&&o.disabled),disabled:F,tabIndex:null,role:void 0,onFocus:function(e){x&&x(e),L&&L.onFocus&&L.onFocus(e)},onBlur:function(e){f&&f(e),L&&L.onBlur&&L.onBlur(e)},ref:a},I),i.createElement("input",(0,k.Z)({autoFocus:n,checked:t,defaultChecked:c,className:o.input,disabled:F,id:M&&u,name:m,onChange:function(e){var a=e.target.checked;z(a),v&&v(e,a)},readOnly:y,ref:p,required:g,tabIndex:j,type:b,value:_},h)),B?r:d)})),O=(0,I.Z)({root:{padding:9},checked:{},disabled:{},input:{cursor:"inherit",position:"absolute",opacity:0,width:"100%",height:"100%",top:0,left:0,margin:0,padding:0,zIndex:1}},{name:"PrivateSwitchBase"})(E),B=n(5209),z=(0,B.Z)(i.createElement("path",{d:"M19 5v14H5V5h14m0-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"}),"CheckBoxOutlineBlank"),L=(0,B.Z)(i.createElement("path",{d:"M19 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.11 0 2-.9 2-2V5c0-1.1-.89-2-2-2zm-9 14l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"}),"CheckBox"),F=n(9693),M=(0,B.Z)(i.createElement("path",{d:"M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-2 10H7v-2h10v2z"}),"IndeterminateCheckBox"),A=n(3871),R=i.createElement(L,null),V=i.createElement(z,null),H=i.createElement(M,null),T=i.forwardRef((function(e,a){var n=e.checkedIcon,t=void 0===n?R:n,r=e.classes,o=e.color,s=void 0===o?"secondary":o,c=e.icon,l=void 0===c?V:c,d=e.indeterminate,u=void 0!==d&&d,h=e.indeterminateIcon,p=void 0===h?H:h,m=e.inputProps,f=e.size,v=void 0===f?"medium":f,x=(0,N.Z)(e,["checkedIcon","classes","color","icon","indeterminate","indeterminateIcon","inputProps","size"]),y=u?p:l,g=u?p:t;return i.createElement(O,(0,k.Z)({type:"checkbox",classes:{root:(0,Z.Z)(r.root,r["color".concat((0,A.Z)(s))],u&&r.indeterminate),checked:r.checked,disabled:r.disabled},color:s,inputProps:(0,k.Z)({"data-indeterminate":u},m),icon:i.cloneElement(y,{fontSize:void 0===y.props.fontSize&&"small"===v?v:y.props.fontSize}),checkedIcon:i.cloneElement(g,{fontSize:void 0===g.props.fontSize&&"small"===v?v:g.props.fontSize}),ref:a},x))})),D=(0,I.Z)((function(e){return{root:{color:e.palette.text.secondary},checked:{},disabled:{},indeterminate:{},colorPrimary:{"&$checked":{color:e.palette.primary.main,"&:hover":{backgroundColor:(0,F.U1)(e.palette.primary.main,e.palette.action.hoverOpacity),"@media (hover: none)":{backgroundColor:"transparent"}}},"&$disabled":{color:e.palette.action.disabled}},colorSecondary:{"&$checked":{color:e.palette.secondary.main,"&:hover":{backgroundColor:(0,F.U1)(e.palette.secondary.main,e.palette.action.hoverOpacity),"@media (hover: none)":{backgroundColor:"transparent"}}},"&$disabled":{color:e.palette.action.disabled}}}}),{name:"MuiCheckbox"})(T),W={h1:"h1",h2:"h2",h3:"h3",h4:"h4",h5:"h5",h6:"h6",subtitle1:"h6",subtitle2:"h6",body1:"p",body2:"p"},q=i.forwardRef((function(e,a){var n=e.align,t=void 0===n?"inherit":n,r=e.classes,o=e.className,s=e.color,c=void 0===s?"initial":s,l=e.component,d=e.display,u=void 0===d?"initial":d,h=e.gutterBottom,p=void 0!==h&&h,m=e.noWrap,f=void 0!==m&&m,v=e.paragraph,x=void 0!==v&&v,y=e.variant,g=void 0===y?"body1":y,j=e.variantMapping,b=void 0===j?W:j,_=(0,N.Z)(e,["align","classes","className","color","component","display","gutterBottom","noWrap","paragraph","variant","variantMapping"]),S=l||(x?"p":b[g]||W[g])||"span";return i.createElement(S,(0,k.Z)({className:(0,Z.Z)(r.root,o,"inherit"!==g&&r[g],"initial"!==c&&r["color".concat((0,A.Z)(c))],f&&r.noWrap,p&&r.gutterBottom,x&&r.paragraph,"inherit"!==t&&r["align".concat((0,A.Z)(t))],"initial"!==u&&r["display".concat((0,A.Z)(u))]),ref:a},_))})),J=(0,I.Z)((function(e){return{root:{margin:0},body2:e.typography.body2,body1:e.typography.body1,caption:e.typography.caption,button:e.typography.button,h1:e.typography.h1,h2:e.typography.h2,h3:e.typography.h3,h4:e.typography.h4,h5:e.typography.h5,h6:e.typography.h6,subtitle1:e.typography.subtitle1,subtitle2:e.typography.subtitle2,overline:e.typography.overline,srOnly:{position:"absolute",height:1,width:1,overflow:"hidden"},alignLeft:{textAlign:"left"},alignCenter:{textAlign:"center"},alignRight:{textAlign:"right"},alignJustify:{textAlign:"justify"},noWrap:{overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap"},gutterBottom:{marginBottom:"0.35em"},paragraph:{marginBottom:16},colorInherit:{color:"inherit"},colorPrimary:{color:e.palette.primary.main},colorSecondary:{color:e.palette.secondary.main},colorTextPrimary:{color:e.palette.text.primary},colorTextSecondary:{color:e.palette.text.secondary},colorError:{color:e.palette.error.main},displayInline:{display:"inline"},displayBlock:{display:"block"}}}),{name:"MuiTypography"})(q),U=i.forwardRef((function(e,a){e.checked;var n=e.classes,t=e.className,r=e.control,o=e.disabled,s=(e.inputRef,e.label),c=e.labelPlacement,l=void 0===c?"end":c,d=(e.name,e.onChange,e.value,(0,N.Z)(e,["checked","classes","className","control","disabled","inputRef","label","labelPlacement","name","onChange","value"])),u=(0,C.Z)(),h=o;"undefined"===typeof h&&"undefined"!==typeof r.props.disabled&&(h=r.props.disabled),"undefined"===typeof h&&u&&(h=u.disabled);var p={disabled:h};return["checked","name","onChange","value","inputRef"].forEach((function(a){"undefined"===typeof r.props[a]&&"undefined"!==typeof e[a]&&(p[a]=e[a])})),i.createElement("label",(0,k.Z)({className:(0,Z.Z)(n.root,t,"end"!==l&&n["labelPlacement".concat((0,A.Z)(l))],h&&n.disabled),ref:a},d),i.cloneElement(r,p),i.createElement(J,{component:"span",className:(0,Z.Z)(n.label,h&&n.disabled)},s))})),G=(0,I.Z)((function(e){return{root:{display:"inline-flex",alignItems:"center",cursor:"pointer",verticalAlign:"middle",WebkitTapHighlightColor:"transparent",marginLeft:-11,marginRight:16,"&$disabled":{cursor:"default"}},labelPlacementStart:{flexDirection:"row-reverse",marginLeft:16,marginRight:-11},labelPlacementTop:{flexDirection:"column-reverse",marginLeft:16},labelPlacementBottom:{flexDirection:"column",marginLeft:16},disabled:{},label:{"&$disabled":{color:e.palette.text.disabled}}}}),{name:"MuiFormControlLabel"})(U),$=n(7879);function K(e,a){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var t=Object.getOwnPropertySymbols(e);a&&(t=t.filter((function(a){return Object.getOwnPropertyDescriptor(e,a).enumerable}))),n.push.apply(n,t)}return n}function X(e){for(var a=1;a<arguments.length;a++){var n=null!=arguments[a]?arguments[a]:{};a%2?K(Object(n),!0).forEach((function(a){(0,s.Z)(e,a,n[a])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):K(Object(n)).forEach((function(a){Object.defineProperty(e,a,Object.getOwnPropertyDescriptor(n,a))}))}return e}function Q(e){return Y.apply(this,arguments)}function Y(){return(Y=(0,l.Z)(r().mark((function e(a){var n;return r().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return n=v().getJSON("token"),e.next=3,x.h.post(a,n).then((function(e){return e.data.reverse()}));case 3:return e.abrupt("return",e.sent);case 4:case"end":return e.stop()}}),e)})))).apply(this,arguments)}function ee(){var e=(0,i.useContext)(m.V),a=(e.subAtivity,e.setSubAtivity,e.authenticated),n=(0,g.useRouter)();(0,i.useEffect)((function(){a||n.push("/login")}),[]);var t=i.useState({checkedA:!0}),r=(0,c.Z)(t,2),l=r[0],d=r[1],f=function(e){d(X(X({},l),{},(0,s.Z)({},e.target.name,e.target.checked)))},v=(0,y.ZP)("activities/list",Q),x=v.data,k=v.error,N=(0,i.useState)(""),Z=N[0],S=N[1],P=x;return console.log(x),k?(0,o.jsx)(j.Z,{}):x?(0,o.jsxs)("div",{className:u().container,children:[(0,o.jsx)(h.default,{children:(0,o.jsx)("title",{children:"Home"})}),(0,o.jsx)("div",{className:u().header,children:(0,o.jsx)(p.Z,{})}),(0,o.jsxs)("div",{className:u().Feed,children:[(0,o.jsxs)("div",{className:u().searchBar,children:[(0,o.jsx)("button",{children:(0,o.jsx)(b.Z,{fontSize:"large"})}),(0,o.jsx)("input",{className:u().formP,name:"pesquisa",placeholder:"Pesquisa",onChange:function(e){return function(e){S(e.target.value),console.log(Z)}(e)}}),(0,o.jsx)(_.Z,{fontSize:"large"}),(0,o.jsxs)("select",{className:u().select,name:"distrito",id:"Portugal",children:[(0,o.jsx)("option",{value:"Portugal",children:"Portugal"}),(0,o.jsx)("option",{value:"Aveiro",children:"Aveiro"}),(0,o.jsx)("option",{value:"Beja",children:"Beja"}),(0,o.jsx)("option",{value:"Braga",children:"Braga"}),(0,o.jsx)("option",{value:"Bragan\xe7a",children:"Bragan\xe7a"}),(0,o.jsx)("option",{value:"Castelo Branco",children:"Castelo Branco"}),(0,o.jsx)("option",{value:"Coimbra",children:"Coimbra"}),(0,o.jsx)("option",{value:"\xc9vora",children:"\xc9vora"}),(0,o.jsx)("option",{value:"Faro",children:"Faro"}),(0,o.jsx)("option",{value:"Guarda",children:"Guarda"}),(0,o.jsx)("option",{value:"Leiria",children:"Leiria"}),(0,o.jsx)("option",{value:"Lisboa",children:"Lisboa"}),(0,o.jsx)("option",{value:"Portalegre",children:"Portalegre"}),(0,o.jsx)("option",{value:"Porto",children:"Porto"}),(0,o.jsx)("option",{value:"Santar\xe9m",children:"Santar\xe9m"}),(0,o.jsx)("option",{value:"Set\xfabal",children:"Set\xfabal"}),(0,o.jsx)("option",{value:"Viana do Castelo",children:"Viana do Castelo"}),(0,o.jsx)("option",{value:"Vila Real",children:"Vila Real"}),(0,o.jsx)("option",{value:"Viseu",children:"Viseu"}),(0,o.jsx)("option",{value:"Ilha da Madeira",children:"Ilha da Madeira"}),(0,o.jsx)("option",{value:"Ilha de Porto Santo",children:"Ilha de Porto Santo"}),(0,o.jsx)("option",{value:"Ilha de Santa Maria",children:"Ilha de Santa Maria"}),(0,o.jsx)("option",{value:"Ilha de S\xe3o Miguel",children:"Ilha de S\xe3o Miguel"}),(0,o.jsx)("option",{value:"Ilha Terceira",children:"Ilha Terceira"}),(0,o.jsx)("option",{value:"Ilha da Graciosa",children:"Ilha da Graciosa"}),(0,o.jsx)("option",{value:"Ilha de S\xe3o Jorge",children:"Ilha de S\xe3o Jorge"}),(0,o.jsx)("option",{value:"Ilha do Pico",children:"Ilha do Pico"}),(0,o.jsx)("option",{value:"Ilha do Faial",children:"Ilha do Faial"}),(0,o.jsx)("option",{value:"Ilha das Flores",children:"Ilha das Flores"}),(0,o.jsx)("option",{value:"Ilha do Corvo",children:"Ilha do Corvo"})]})]}),(0,o.jsx)("div",{children:P.map((function(e,a){return(0,i.createElement)($.Z,X(X({},e),{},{key:a}))}))})]}),(0,o.jsxs)("div",{className:u().other,children:[(0,o.jsx)("h1",{children:"Filtros"}),(0,o.jsx)(G,{className:u().filters,control:(0,o.jsx)(D,{checked:l.checkedA,onChange:f,name:"checkedA",color:"default"}),label:"Mostrar apenas atividades das organiza\xe7\xf5es que sigo"}),(0,o.jsx)(G,{className:u().filters,control:(0,o.jsx)(D,{checked:l.checkedA,onChange:f,name:"checkedA",color:"default"}),label:"Mostrar apenas atividades de organiza\xe7\xf5es"}),(0,o.jsx)(G,{className:u().filters,control:(0,o.jsx)(D,{checked:l.checkedA,onChange:f,name:"checkedA",color:"default"}),label:"Mostrar apenas atividades de indiv\xedduos"}),(0,o.jsx)("br",{})]})]}):(0,o.jsx)("div",{children:"loading..."})}},1344:function(e,a,n){(window.__NEXT_P=window.__NEXT_P||[]).push(["/home",function(){return n(3431)}])},5558:function(e){e.exports={container:"styles_container__2X-P5",avatarandname:"styles_avatarandname__1etW-",userimage:"styles_userimage__1mG5Q",image:"styles_image__prNSZ",username:"styles_username__3U9Ig",name:"styles_name__2KULe",arroba:"styles_arroba__3Z_fm",activity:"styles_activity__3upyM",activityinfo:"styles_activityinfo__3l_Xi",localdate:"styles_localdate__3HTz0",participants:"styles_participants__19iSN",vermaiscontainer:"styles_vermaiscontainer__22Zkw"}},7623:function(e){e.exports={NavbarItems:"styles_NavbarItems__1mok6",Navbar:"styles_Navbar__20cRx",topics:"styles_topics__3HcYs",links:"styles_links__WJ5oK",linkname:"styles_linkname__YKF9m"}},5665:function(e){e.exports={container:"base_container__3b-bk",header:"base_header__1vI13",Feed:"base_Feed__1i9Wq",searchBar:"base_searchBar__1ng96",activity:"base_activity__1pnCS",formP:"base_formP__2lKGF",other:"base_other__npAFr",filters:"base_filters__3ShyN",banneravatar:"base_banneravatar__2H-c0"}},4453:function(){}},function(e){e.O(0,[774,445,260,13,351,433,321,15,996,912,723,605,32],(function(){return a=1344,e(e.s=a);var a}));var a=e.O();_N_E=a}]);