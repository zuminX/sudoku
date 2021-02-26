client.test("请求成功执行", function() {
  client.assert(response.status === 200, "响应状态不为200");
  var body = response.body;
  client.assert(body.success === true, "请求结果中是否成功字段不为true");
  client.assert(body.httpStatus === 200, "请求结果中http请求状态字段不为200");
});