self["webpackHotUpdate_N_E"]("pages/home",{

/***/ "./src/Components/Header/index.tsx":
/*!*****************************************!*\
  !*** ./src/Components/Header/index.tsx ***!
  \*****************************************/
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": function() { return /* binding */ NavBar; }
/* harmony export */ });
/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react/jsx-dev-runtime */ "./node_modules/react/jsx-dev-runtime.js");
/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _menuitems__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./menuitems */ "./src/Components/Header/menuitems.ts");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! react */ "./node_modules/react/index.js");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _styles_module_scss__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./styles.module.scss */ "./src/Components/Header/styles.module.scss");
/* harmony import */ var _styles_module_scss__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(_styles_module_scss__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var next_link__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! next/link */ "./node_modules/next/link.js");
/* harmony import */ var next_link__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(next_link__WEBPACK_IMPORTED_MODULE_3__);
/* harmony import */ var _Context_AuthContext__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../Context/AuthContext */ "./src/Context/AuthContext.tsx");
/* module decorator */ module = __webpack_require__.hmd(module);


var _jsxFileName = "C:\\Users\\Ang3lExtreme\\Documents\\react\\setuphh\\src\\Components\\Header\\index.tsx",
    _s = $RefreshSig$();







function NavBar() {
  _s();

  var _this = this;

  var _useState = (0,react__WEBPACK_IMPORTED_MODULE_2__.useState)(false),
      state = _useState[0],
      setState = _useState[1];

  var _useContext = (0,react__WEBPACK_IMPORTED_MODULE_2__.useContext)(_Context_AuthContext__WEBPACK_IMPORTED_MODULE_4__.AuthContext),
      handleLogout = _useContext.handleLogout;

  var handleClick = function handleClick() {
    setState(!state);
  };

  return /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("div", {
    className: (_styles_module_scss__WEBPACK_IMPORTED_MODULE_5___default().Container),
    children: /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("div", {
      className: (_styles_module_scss__WEBPACK_IMPORTED_MODULE_5___default().NavbarItems),
      children: /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("nav", {
        children: [/*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("h1", {
          children: "Helpin'Hand"
        }, void 0, false, {
          fileName: _jsxFileName,
          lineNumber: 21,
          columnNumber: 11
        }, this), /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("div", {
          className: (_styles_module_scss__WEBPACK_IMPORTED_MODULE_5___default().Navbar),
          children: [/*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("ul", {
            children: _menuitems__WEBPACK_IMPORTED_MODULE_1__.menu.map(function (item, index) {
              return /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("li", {
                children: /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)((next_link__WEBPACK_IMPORTED_MODULE_3___default()), {
                  href: item.url,
                  children: /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("a", {
                    className: item.cName,
                    children: item.title
                  }, void 0, false, {
                    fileName: _jsxFileName,
                    lineNumber: 28,
                    columnNumber: 44
                  }, _this)
                }, void 0, false, {
                  fileName: _jsxFileName,
                  lineNumber: 28,
                  columnNumber: 21
                }, _this)
              }, index, false, {
                fileName: _jsxFileName,
                lineNumber: 26,
                columnNumber: 19
              }, _this);
            })
          }, void 0, false, {
            fileName: _jsxFileName,
            lineNumber: 23,
            columnNumber: 13
          }, this), /*#__PURE__*/(0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)("button", {
            onClick: function onClick() {
              return handleLogout();
            },
            children: "Logout"
          }, void 0, false, {
            fileName: _jsxFileName,
            lineNumber: 38,
            columnNumber: 13
          }, this)]
        }, void 0, true, {
          fileName: _jsxFileName,
          lineNumber: 22,
          columnNumber: 11
        }, this)]
      }, void 0, true, {
        fileName: _jsxFileName,
        lineNumber: 20,
        columnNumber: 9
      }, this)
    }, void 0, false, {
      fileName: _jsxFileName,
      lineNumber: 19,
      columnNumber: 7
    }, this)
  }, void 0, false, {
    fileName: _jsxFileName,
    lineNumber: 18,
    columnNumber: 5
  }, this);
}

_s(NavBar, "CkkwfbQSXaa13/PNVP3rx0Wq+vw=");

_c = NavBar;

var _c;

$RefreshReg$(_c, "NavBar");

;
    var _a, _b;
    // Legacy CSS implementations will `eval` browser code in a Node.js context
    // to extract CSS. For backwards compatibility, we need to check we're in a
    // browser context before continuing.
    if (typeof self !== 'undefined' &&
        // AMP / No-JS mode does not inject these helpers:
        '$RefreshHelpers$' in self) {
        var currentExports = module.__proto__.exports;
        var prevExports = (_b = (_a = module.hot.data) === null || _a === void 0 ? void 0 : _a.prevExports) !== null && _b !== void 0 ? _b : null;
        // This cannot happen in MainTemplate because the exports mismatch between
        // templating and execution.
        self.$RefreshHelpers$.registerExportsForReactRefresh(currentExports, module.id);
        // A module can be accepted automatically based on its exports, e.g. when
        // it is a Refresh Boundary.
        if (self.$RefreshHelpers$.isReactRefreshBoundary(currentExports)) {
            // Save the previous exports on update so we can compare the boundary
            // signatures.
            module.hot.dispose(function (data) {
                data.prevExports = currentExports;
            });
            // Unconditionally accept an update to this module, we'll check if it's
            // still a Refresh Boundary later.
            module.hot.accept();
            // This field is set when the previous version of this module was a
            // Refresh Boundary, letting us know we need to check for invalidation or
            // enqueue an update.
            if (prevExports !== null) {
                // A boundary can become ineligible if its exports are incompatible
                // with the previous exports.
                //
                // For example, if you add/remove/change exports, we'll want to
                // re-execute the importing modules, and force those components to
                // re-render. Similarly, if you convert a class component to a
                // function, we want to invalidate the boundary.
                if (self.$RefreshHelpers$.shouldInvalidateReactRefreshBoundary(prevExports, currentExports)) {
                    module.hot.invalidate();
                }
                else {
                    self.$RefreshHelpers$.scheduleUpdate();
                }
            }
        }
        else {
            // Since we just executed the code for the module, it's possible that the
            // new exports made it ineligible for being a boundary.
            // We only care about the case when we were _previously_ a boundary,
            // because we already accepted this update (accidental side effect).
            var isNoLongerABoundary = prevExports !== null;
            if (isNoLongerABoundary) {
                module.hot.invalidate();
            }
        }
    }


/***/ })

});
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly9fTl9FLy4vc3JjL0NvbXBvbmVudHMvSGVhZGVyL2luZGV4LnRzeCJdLCJuYW1lcyI6WyJOYXZCYXIiLCJ1c2VTdGF0ZSIsInN0YXRlIiwic2V0U3RhdGUiLCJ1c2VDb250ZXh0IiwiQXV0aENvbnRleHQiLCJoYW5kbGVMb2dvdXQiLCJoYW5kbGVDbGljayIsInN0eWxlcyIsIm1lbnUiLCJpdGVtIiwiaW5kZXgiLCJ1cmwiLCJjTmFtZSIsInRpdGxlIl0sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OztBQUFBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNlLFNBQVNBLE1BQVQsR0FBa0I7QUFBQTs7QUFBQTs7QUFBQSxrQkFHTEMsK0NBQVEsQ0FBQyxLQUFELENBSEg7QUFBQSxNQUd4QkMsS0FId0I7QUFBQSxNQUdqQkMsUUFIaUI7O0FBQUEsb0JBSU5DLGlEQUFVLENBQUNDLDZEQUFELENBSko7QUFBQSxNQUl2QkMsWUFKdUIsZUFJdkJBLFlBSnVCOztBQUsvQixNQUFNQyxXQUFXLEdBQUcsU0FBZEEsV0FBYyxHQUFNO0FBQ3hCSixZQUFRLENBQUMsQ0FBQ0QsS0FBRixDQUFSO0FBQ0QsR0FGRDs7QUFLQSxzQkFDRTtBQUFLLGFBQVMsRUFBRU0sc0VBQWhCO0FBQUEsMkJBQ0U7QUFBSyxlQUFTLEVBQUVBLHdFQUFoQjtBQUFBLDZCQUNFO0FBQUEsZ0NBQ0U7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUEsZ0JBREYsZUFFRTtBQUFLLG1CQUFTLEVBQUVBLG1FQUFoQjtBQUFBLGtDQUNFO0FBQUEsc0JBQ0dDLGdEQUFBLENBQVMsVUFBQ0MsSUFBRCxFQUFPQyxLQUFQLEVBQWlCO0FBQ3pCLGtDQUNFO0FBQUEsdUNBRUUsOERBQUMsa0RBQUQ7QUFBTSxzQkFBSSxFQUFFRCxJQUFJLENBQUNFLEdBQWpCO0FBQUEseUNBQXVCO0FBQUcsNkJBQVMsRUFBRUYsSUFBSSxDQUFDRyxLQUFuQjtBQUFBLDhCQUVyQkgsSUFBSSxDQUFDSTtBQUZnQjtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQXZCO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFGRixpQkFBU0gsS0FBVDtBQUFBO0FBQUE7QUFBQTtBQUFBLHVCQURGO0FBVUQsYUFYQTtBQURIO0FBQUE7QUFBQTtBQUFBO0FBQUEsa0JBREYsZUFnQkU7QUFBUSxtQkFBTyxFQUFFO0FBQUEscUJBQU1MLFlBQVksRUFBbEI7QUFBQSxhQUFqQjtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQSxrQkFoQkY7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLGdCQUZGO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQURGO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFERjtBQUFBO0FBQUE7QUFBQTtBQUFBLFVBREY7QUE2QkQ7O0dBdkN1Qk4sTTs7S0FBQUEsTSIsImZpbGUiOiJzdGF0aWMvd2VicGFjay9wYWdlcy9ob21lLjg4OTBmYTllNWQ1ZmQwYjYyM2Q0LmhvdC11cGRhdGUuanMiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBtZW51IH0gZnJvbSAnLi9tZW51aXRlbXMnXHJcbmltcG9ydCB7IHVzZVN0YXRlIH0gZnJvbSAncmVhY3QnO1xyXG5pbXBvcnQgc3R5bGVzIGZyb20gJy4vc3R5bGVzLm1vZHVsZS5zY3NzJ1xyXG5pbXBvcnQgTGluayBmcm9tICduZXh0L2xpbmsnO1xyXG5pbXBvcnQgeyBBdXRoQ29udGV4dCB9IGZyb20gJy4uLy4uL0NvbnRleHQvQXV0aENvbnRleHQnXHJcbmltcG9ydCBSZWFjdCwgeyB1c2VDb250ZXh0IH0gZnJvbSAncmVhY3QnXHJcbmV4cG9ydCBkZWZhdWx0IGZ1bmN0aW9uIE5hdkJhcigpIHtcclxuXHJcblxyXG4gIGNvbnN0IFtzdGF0ZSwgc2V0U3RhdGVdID0gdXNlU3RhdGUoZmFsc2UpXHJcbiAgY29uc3QgeyBoYW5kbGVMb2dvdXQgfSA9IHVzZUNvbnRleHQoQXV0aENvbnRleHQpO1xyXG4gIGNvbnN0IGhhbmRsZUNsaWNrID0gKCkgPT4ge1xyXG4gICAgc2V0U3RhdGUoIXN0YXRlKVxyXG4gIH1cclxuXHJcblxyXG4gIHJldHVybiAoXHJcbiAgICA8ZGl2IGNsYXNzTmFtZT17c3R5bGVzLkNvbnRhaW5lcn0+XHJcbiAgICAgIDxkaXYgY2xhc3NOYW1lPXtzdHlsZXMuTmF2YmFySXRlbXN9PlxyXG4gICAgICAgIDxuYXYgPlxyXG4gICAgICAgICAgPGgxPkhlbHBpbidIYW5kPC9oMT5cclxuICAgICAgICAgIDxkaXYgY2xhc3NOYW1lPXtzdHlsZXMuTmF2YmFyfT5cclxuICAgICAgICAgICAgPHVsPlxyXG4gICAgICAgICAgICAgIHttZW51Lm1hcCgoaXRlbSwgaW5kZXgpID0+IHtcclxuICAgICAgICAgICAgICAgIHJldHVybiAoXHJcbiAgICAgICAgICAgICAgICAgIDxsaSBrZXk9e2luZGV4fT5cclxuXHJcbiAgICAgICAgICAgICAgICAgICAgPExpbmsgaHJlZj17aXRlbS51cmx9ID48YSBjbGFzc05hbWU9e2l0ZW0uY05hbWV9XHJcblxyXG4gICAgICAgICAgICAgICAgICAgID57aXRlbS50aXRsZX08L2E+PC9MaW5rPlxyXG5cclxuICAgICAgICAgICAgICAgICAgPC9saT5cclxuXHJcbiAgICAgICAgICAgICAgICApXHJcbiAgICAgICAgICAgICAgfSl9XHJcblxyXG4gICAgICAgICAgICA8L3VsPlxyXG4gICAgICAgICAgICA8YnV0dG9uIG9uQ2xpY2s9eygpID0+IGhhbmRsZUxvZ291dCgpfT5Mb2dvdXQ8L2J1dHRvbj5cclxuICAgICAgICAgIDwvZGl2PlxyXG5cclxuICAgICAgICA8L25hdj5cclxuICAgICAgPC9kaXY+XHJcbiAgICA8L2RpdiA+XHJcblxyXG4gIClcclxufSJdLCJzb3VyY2VSb290IjoiIn0=