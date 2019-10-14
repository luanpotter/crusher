import 'package:flutter/material.dart';

import '../login.dart';
import '../util.dart';

class LoginPage extends StatefulWidget {
  final void Function(Login) callback;

  const LoginPage({Key key, this.callback}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  bool loading = false;

  @override
  Widget build(BuildContext context) {
    return loader(loading, content());
  }

  Widget content() {
    return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Login.button(() async {
              try {
                this.setState(() => loading = true);
                Login user = await Login.doSignIn();
                widget.callback(user);
                print("Success, email: ${user.email}");
              } catch (ex) {
                print('Error: $ex');
              }
            }),
          ],
        ),
      );
  }
}