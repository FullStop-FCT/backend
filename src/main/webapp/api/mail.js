(function() {
var exports = {};
exports.id = 70;
exports.ids = [70];
exports.modules = {

/***/ 2691:
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
const mail = __webpack_require__(5511);

mail.setApiKey("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");
/* harmony default export */ __webpack_exports__["default"] = ((req, res) => {
  const body = JSON.parse(req.body);
  const message = `
        Nome: ${body.nome}\r\n
        Email: ${body.email}\r\n
        Assunto: ${body.assunto}\r\n
        Mensagem: ${body.mensagem}
        `;
  console.log(message);
  const data = {
    to: 'fullstophh@gmail.com',
    from: 'HelpinHand@fullstop.website',
    subject: `HelpinHand ${body.email}`,
    text: message,
    html: message.replace(/\r\n/g, '<br>')
  };
  mail.send(data);
  res.status(200).json({
    status: 'Ok'
  });
});

/***/ }),

/***/ 5511:
/***/ (function(module) {

"use strict";
module.exports = require("@sendgrid/mail");;

/***/ })

};
;

// load runtime
var __webpack_require__ = require("../../webpack-runtime.js");
__webpack_require__.C(exports);
var __webpack_exec__ = function(moduleId) { return __webpack_require__(__webpack_require__.s = moduleId); }
var __webpack_exports__ = (__webpack_exec__(2691));
module.exports = __webpack_exports__;

})();