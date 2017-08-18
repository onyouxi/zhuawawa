var express = require('express');
var router = express.Router();

/**跳转到主页*/
router.get('/', function(req, res, next) {
  res.render('wawa/wawa', { title: 'Express' });
});
/**跳转到登录页*/
router.get('/login', function(req, res, next) {
  res.render('login');
});

/**跳转到登录页*/
router.get('/user', function(req, res, next) {
  res.render('user/user');
});

router.get('/reply', function(req, res, next) {
  res.render('wechat/wechatReply');
});

/**管理员*/
router.get('/adminmanager', function(req, res, next) {
  res.render('adminmanager/index');
});

/**打赏**/
router.get('/reward', function(req, res, next) {
  res.render('reward/reward');
});


router.get('/order', function(req, res, next) {
    res.render('order/order');
});

router.get('/wawa', function(req, res, next) {
  res.render('wawa/wawa');
});


module.exports = router;
