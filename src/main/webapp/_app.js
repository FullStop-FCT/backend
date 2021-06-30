(function() {
var exports = {};
exports.id = 888;
exports.ids = [888];
exports.modules = {

/***/ 6785:
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

"use strict";

// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "h": function() { return /* binding */ api; },
  "d": function() { return /* binding */ storageProfilePic; }
});

;// CONCATENATED MODULE: external "axios"
var external_axios_namespaceObject = require("axios");;
var external_axios_default = /*#__PURE__*/__webpack_require__.n(external_axios_namespaceObject);
;// CONCATENATED MODULE: ./services/api.ts

const api = external_axios_default().create({
  baseURL: 'https://helpin-hand.ey.r.appspot.com/rest/'
});
const storageProfilePic = external_axios_default().create({
  baseURL: 'https://helpin-hand.ey.r.appspot.com/gcs/imagens-helpin-hand/'
});

/***/ }),

/***/ 7569:
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "V": function() { return /* binding */ AuthContext; },
/* harmony export */   "H": function() { return /* binding */ AuthContextProvider; }
/* harmony export */ });
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5282);
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(9297);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _services_api__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(6785);
/* harmony import */ var next_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(6731);
/* harmony import */ var next_router__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(next_router__WEBPACK_IMPORTED_MODULE_3__);
/* harmony import */ var js_cookie__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(6155);
/* harmony import */ var js_cookie__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(js_cookie__WEBPACK_IMPORTED_MODULE_4__);





const AuthContext = /*#__PURE__*/(0,react__WEBPACK_IMPORTED_MODULE_1__.createContext)({});
function AuthContextProvider({
  children
}) {
  const router = (0,next_router__WEBPACK_IMPORTED_MODULE_3__.useRouter)();
  const {
    0: authenticated,
    1: setAuthenticated
  } = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(false);
  const {
    0: loading,
    1: setLoading
  } = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(true);
  const {
    0: subAtivity,
    1: setSubAtivity
  } = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(false);
  const {
    0: subEdit,
    1: setSubEdit
  } = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(false);
  (0,react__WEBPACK_IMPORTED_MODULE_1__.useEffect)(() => {
    const token = js_cookie__WEBPACK_IMPORTED_MODULE_4___default().get('token');

    if (token) {
      //api.defaults.headers.Authorization = `Bearer ${JSON.parse(token)}`;
      setAuthenticated(true);
    } else {
      setAuthenticated(false);
    }

    setLoading(false);
  }, []);

  async function handleLogout() {
    setAuthenticated(false);
    js_cookie__WEBPACK_IMPORTED_MODULE_4___default().remove('token');
    js_cookie__WEBPACK_IMPORTED_MODULE_4___default().remove('user');
    router.push('/');
  }

  async function handleLogin(data) {
    //const router = useRouter();
    await _services_api__WEBPACK_IMPORTED_MODULE_2__/* .api.post */ .h.post('authentication/login', data).then(function (response) {
      if (response.data) {
        console.log(response);
        js_cookie__WEBPACK_IMPORTED_MODULE_4___default().set('token', JSON.stringify(response.data));
        js_cookie__WEBPACK_IMPORTED_MODULE_4___default().set('user', response.data.username); //add date-fns
        //api.defaults.headers.Authorization = `Bearer ${response.data.tokenID}`;
        //console.log(response.data.username)

        setAuthenticated(true);
        router.push(`/${response.data.username}`);
      }
    }).catch(function (error) {
      console.log(error);
      setAuthenticated(false);
    });
  }

  if (loading) {
    return /*#__PURE__*/react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx("h1", {
      children: "Loading"
    });
  }

  return /*#__PURE__*/react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(AuthContext.Provider, {
    value: {
      authenticated,
      handleLogin,
      handleLogout,
      subAtivity,
      setSubAtivity,
      subEdit,
      setSubEdit
    },
    children: children
  });
}

/***/ }),

/***/ 3655:
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "X": function() { return /* binding */ MapContext; },
/* harmony export */   "s": function() { return /* binding */ MapContextProvider; }
/* harmony export */ });
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5282);
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(9297);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);


const MapContext = /*#__PURE__*/(0,react__WEBPACK_IMPORTED_MODULE_1__.createContext)({});
function MapContextProvider({
  children
}) {
  const {
    0: activityLocation,
    1: setActivityLocation
  } = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)("");
  const {
    0: markers,
    1: setMarkers
  } = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)({
    lat: 0,
    lng: 0
  });
  return /*#__PURE__*/react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(MapContext.Provider, {
    value: {
      activityLocation,
      setActivityLocation,
      markers,
      setMarkers
    },
    children: children
  });
}

/***/ }),

/***/ 7370:
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5282);
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(9297);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _Context_AuthContext__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(7569);
/* harmony import */ var _Context_MapContext__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(3655);


function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }






function MyApp({
  Component,
  pageProps
}) {
  return /*#__PURE__*/react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_Context_AuthContext__WEBPACK_IMPORTED_MODULE_2__/* .AuthContextProvider */ .H, {
    children: /*#__PURE__*/react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_Context_MapContext__WEBPACK_IMPORTED_MODULE_3__/* .MapContextProvider */ .s, {
      children: /*#__PURE__*/react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(Component, _objectSpread({}, pageProps))
    })
  });
}

/* harmony default export */ __webpack_exports__["default"] = (MyApp);

/***/ }),

/***/ 6155:
/***/ (function(module) {

"use strict";
module.exports = require("js-cookie");;

/***/ }),

/***/ 6731:
/***/ (function(module) {

"use strict";
module.exports = require("next/router");;

/***/ }),

/***/ 9297:
/***/ (function(module) {

"use strict";
module.exports = require("react");;

/***/ }),

/***/ 5282:
/***/ (function(module) {

"use strict";
module.exports = require("react/jsx-runtime");;

/***/ })

};
;

// load runtime
var __webpack_require__ = require("../webpack-runtime.js");
__webpack_require__.C(exports);
var __webpack_exec__ = function(moduleId) { return __webpack_require__(__webpack_require__.s = moduleId); }
var __webpack_exports__ = (__webpack_exec__(7370));
module.exports = __webpack_exports__;

})();