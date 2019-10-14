import 'dart:convert' show json;
import 'package:crusher/api/api_exception.dart';
import 'package:http/http.dart' as http;

import '../login.dart';
import '../model/cron.dart';

class CronApi {
  static const BASE = 'https://crusher-app.herokuapp.com/';

  static Future<List<Cron>> fetchCrons(Login user) async {
    final response =
        await http.get('$BASE/crons', headers: await _headers(user));
    _checkStatusCode(response);
    final List list = (json.decode(response.body) as List);
    return list.map((m) => Cron.fromJson(m)).toList();
  }

  static Future<Cron> createCron(Login user, Cron cron) async {
    final String body = json.encode(cron.toMap());
    final response = await http.post('$BASE/crons',
        body: body, headers: await _headers(user));
    _checkStatusCode(response);
    return Cron.fromJson(json.decode(response.body) as Map);
  }

  static Future<void> deleteCron(Login user, Cron cron) async {
    final http.Response response = await http.delete('$BASE/crons/${cron.id}',
        headers: await _headers(user));
    _checkStatusCode(response);
  }

  static Future<Map<String, String>> _headers(Login user) async {
    final String token = await user.getToken();
    final Map<String, String> headers = {'Authorization': 'Bearer $token'};
    return headers;
  }

  static void _checkStatusCode(http.Response response) {
    if (response.statusCode != 200) throw ApiException(response);
  }
}
