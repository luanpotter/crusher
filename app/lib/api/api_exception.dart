import 'package:http/http.dart' as http;

class ApiException {
  int status;
  String message;

  ApiException(http.Response ex) {
    this.status = ex.statusCode;
    this.message = ex.body;
  }

  @override
  String toString() => '($status): $message';
}