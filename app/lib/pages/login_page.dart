import 'package:flutter/material.dart';

import '../login.dart';

class LoginPage extends StatelessWidget {
  final void Function(Login) callback;

  const LoginPage({Key key, this.callback}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Login.button(() async {
              try {
                Login user = await Login.doSignIn();
                callback(user);
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