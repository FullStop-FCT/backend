(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[7959],{26255:function(t,e,i){"use strict";var n=i(53848),o=i(83115),a=i(62426);e.default=function(t){var e=t.src,i=t.sizes,o=t.unoptimized,a=void 0!==o&&o,s=t.priority,u=void 0!==s&&s,g=t.loading,p=t.className,h=t.quality,m=t.width,v=t.height,b=t.objectFit,y=t.objectPosition,A=t.loader,S=void 0===A?z:A,k=(0,r.default)(t,["src","sizes","unoptimized","priority","loading","className","quality","width","height","objectFit","objectPosition","loader"]),_=i?"responsive":"intrinsic",E=!1;"unsized"in k?(E=Boolean(k.unsized),delete k.unsized):"layout"in k&&(k.layout&&(_=k.layout),delete k.layout);0;var j=!u&&("lazy"===g||"undefined"===typeof g);e&&e.startsWith("data:")&&(a=!0,j=!1);var q,O,D,C=(0,f.useIntersection)({rootMargin:"200px",disabled:!j}),I=n(C,2),B=I[0],M=I[1],N=!j||M,R=x(m),L=x(v),W=x(h),P={position:"absolute",top:0,left:0,bottom:0,right:0,boxSizing:"border-box",padding:0,border:"none",margin:"auto",display:"block",width:0,height:0,minWidth:"100%",maxWidth:"100%",minHeight:"100%",maxHeight:"100%",objectFit:b,objectPosition:y};if("undefined"!==typeof R&&"undefined"!==typeof L&&"fill"!==_){var F=L/R,H=isNaN(F)?"100%":"".concat(100*F,"%");"responsive"===_?(q={display:"block",overflow:"hidden",position:"relative",boxSizing:"border-box",margin:0},O={display:"block",boxSizing:"border-box",paddingTop:H}):"intrinsic"===_?(q={display:"inline-block",maxWidth:"100%",overflow:"hidden",position:"relative",boxSizing:"border-box",margin:0},O={boxSizing:"border-box",display:"block",maxWidth:"100%"},D='<svg width="'.concat(R,'" height="').concat(L,'" xmlns="http://www.w3.org/2000/svg" version="1.1"/>')):"fixed"===_&&(q={overflow:"hidden",boxSizing:"border-box",display:"inline-block",position:"relative",width:R,height:L})}else"undefined"===typeof R&&"undefined"===typeof L&&"fill"===_&&(q={display:"block",overflow:"hidden",position:"absolute",top:0,left:0,bottom:0,right:0,boxSizing:"border-box",margin:0});var V={src:"data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",srcSet:void 0,sizes:void 0};N&&(V=w({src:e,unoptimized:a,layout:_,width:R,quality:W,sizes:i,loader:S}));E&&(q=void 0,O=void 0,P=void 0);return c.default.createElement("div",{style:q},O?c.default.createElement("div",{style:O},D?c.default.createElement("img",{style:{maxWidth:"100%",display:"block",margin:0,border:"none",padding:0},alt:"","aria-hidden":!0,role:"presentation",src:"data:image/svg+xml;base64,".concat((0,l.toBase64)(D))}):null):null,!N&&c.default.createElement("noscript",null,c.default.createElement("img",Object.assign({},k,w({src:e,unoptimized:a,layout:_,width:R,quality:W,sizes:i,loader:S}),{src:e,decoding:"async",sizes:i,style:P,className:p}))),c.default.createElement("img",Object.assign({},k,V,{decoding:"async",className:p,ref:B,style:P})),u?c.default.createElement(d.default,null,c.default.createElement("link",{key:"__nimg-"+V.src+V.srcSet+V.sizes,rel:"preload",as:"image",href:V.srcSet?void 0:V.src,imagesrcset:V.srcSet,imagesizes:V.sizes})):null)};var r=a(i(26169)),s=a(i(9566)),c=a(i(67294)),d=a(i(57947)),l=i(47239),u=i(5655),f=i(75749);var g=new Map([["imgix",function(t){var e=t.root,i=t.src,n=t.width,o=t.quality,a=["auto=format","fit=max","w="+n],r="";o&&a.push("q="+o);a.length&&(r="?"+a.join("&"));return"".concat(e).concat(A(i)).concat(r)}],["cloudinary",function(t){var e=t.root,i=t.src,n=t.width,o=t.quality,a=["f_auto","c_limit","w_"+n,"q_"+(o||"auto")].join(",")+"/";return"".concat(e).concat(a).concat(A(i))}],["akamai",function(t){var e=t.root,i=t.src,n=t.width;return"".concat(e).concat(A(i),"?imwidth=").concat(n)}],["default",function(t){var e=t.root,i=t.src,n=t.width,o=t.quality;0;return"".concat(e,"?url=").concat(encodeURIComponent(i),"&w=").concat(n,"&q=").concat(o||75)}]]),p={deviceSizes:[640,750,828,1080,1200,1920,2048,3840],imageSizes:[16,32,48,64,96,128,256,384],path:"",loader:"imgix"}||u.imageConfigDefault,h=p.deviceSizes,m=p.imageSizes,v=p.loader,b=p.path,y=(p.domains,[].concat(o(h),o(m)));function w(t){var e=t.src,i=t.unoptimized,n=t.layout,a=t.width,r=t.quality,s=t.sizes,c=t.loader;if(i)return{src:e,srcSet:void 0,sizes:void 0};var d=function(t,e,i){if(i&&("fill"===e||"responsive"===e)){var n=o(i.matchAll(/(^|\s)(1?\d?\d)vw/g)).map((function(t){return parseInt(t[2])}));if(n.length){var a=.01*Math.min.apply(Math,o(n));return{widths:y.filter((function(t){return t>=h[0]*a})),kind:"w"}}return{widths:y,kind:"w"}}return"number"!==typeof t||"fill"===e||"responsive"===e?{widths:h,kind:"w"}:{widths:o(new Set([t,2*t].map((function(t){return y.find((function(e){return e>=t}))||y[y.length-1]})))),kind:"x"}}(a,n,s),l=d.widths,u=d.kind,f=l.length-1;return{sizes:s||"w"!==u?s:"100vw",srcSet:l.map((function(t,i){return"".concat(c({src:e,quality:r,width:t})," ").concat("w"===u?t:i+1).concat(u)})).join(", "),src:c({src:e,quality:r,width:l[f]})}}function x(t){return"number"===typeof t?t:"string"===typeof t?parseInt(t,10):void 0}function z(t){var e=g.get(v);if(e)return e((0,s.default)({root:b},t));throw new Error('Unknown "loader" found in "next.config.js". Expected: '.concat(u.VALID_LOADERS.join(", "),". Received: ").concat(v))}function A(t){return"/"===t[0]?t.slice(1):t}h.sort((function(t,e){return t-e})),y.sort((function(t,e){return t-e}))},47239:function(t,e){"use strict";e.__esModule=!0,e.toBase64=function(t){return window.btoa(t)}},5655:function(t,e){"use strict";e.__esModule=!0,e.imageConfigDefault=e.VALID_LOADERS=void 0;e.VALID_LOADERS=["default","imgix","cloudinary","akamai"];e.imageConfigDefault={deviceSizes:[640,750,828,1080,1200,1920,2048,3840],imageSizes:[16,32,48,64,96,128,256,384],path:"/_next/image",loader:"default",domains:[]}},25675:function(t,e,i){t.exports=i(26255)},9566:function(t){function e(){return t.exports=e=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var i=arguments[e];for(var n in i)Object.prototype.hasOwnProperty.call(i,n)&&(t[n]=i[n])}return t},e.apply(this,arguments)}t.exports=e},26169:function(t){t.exports=function(t,e){if(null==t)return{};var i,n,o={},a=Object.keys(t);for(n=0;n<a.length;n++)i=a[n],e.indexOf(i)>=0||(o[i]=t[i]);return o}},66653:function(t,e,i){"use strict";i.d(e,{Sw5:function(){return o}});var n=i(44405);function o(t){return(0,n.w_)({tag:"svg",attr:{viewBox:"0 0 12 16"},child:[{tag:"path",attr:{fillRule:"evenodd",d:"M6 0C2.69 0 0 2.5 0 5.5 0 10.02 6 16 6 16s6-5.98 6-10.5C12 2.5 9.31 0 6 0zm0 14.55C4.14 12.52 1 8.44 1 5.5 1 3.02 3.25 1 6 1c1.34 0 2.61.48 3.56 1.36.92.86 1.44 1.97 1.44 3.14 0 2.94-3.14 7.02-5 9.05zM8 5.5c0 1.11-.89 2-2 2-1.11 0-2-.89-2-2 0-1.11.89-2 2-2 1.11 0 2 .89 2 2z"}}]})(t)}}}]);