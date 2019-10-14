import 'dart:convert' show json;
import 'package:http/http.dart' as http;

import 'login.dart';
import 'model/cron.dart';

class Api {
  static const BASE = 'https://crusher-app.herokuapp.com/';
  static Future<List<Cron>> fetchCrons(Login user) async {
    final http.Response response = await http.get('$BASE/crons', headers: await _headers(user));
    final List list = (json.decode(response.body) as List);
    return list.map((m) => Cron.fromJson(m)).toList();
  }

  static Future<Cron> createCron(Login user, Cron cron) async {
    final String body = json.encode(cron.toMap());
    print(body);
    final http.Response response = await http.post('$BASE/crons', body: body, headers: await _headers(user));
    return Cron.fromJson(json.decode(response.body) as Map);
  }

  static Future<Map<String, String>> _headers(Login user) async {
    final String token = await user.getToken();
    final Map<String, String> headers = { 'Authorization': 'Bearer $token' };
    return headers;
  }
}