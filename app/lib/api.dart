import 'dart:convert' show json;
import 'package:http/http.dart' as http;

import 'login.dart';
import 'model/cron.dart';

class Api {
  static const BASE = 'https://crusher-app.herokuapp.com/';
  static Future<List<Cron>> fetchCrons(Login user) async {
    final String token = await user.getToken();
    final Map<String, String> headers = { 'Authorization': 'Bearer $token' };
    final http.Response response = await http.get('$BASE/crons', headers: headers);
    final List list = (json.decode(response.body) as List);
    return list.map((m) => Cron.fromJson(m)).toList();
  }
}