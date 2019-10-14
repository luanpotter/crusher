import 'package:flutter/material.dart';

import '../login.dart';
import '../util.dart';

class MainPage extends StatelessWidget {
  final Login user;
  final void Function() logout;
  
  const MainPage({Key key, this.user, this.logout}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: pad(buildHeader()),
    );
  }

  Widget buildHeader() {
    return Row(
      children: [
        Text('Logged in as ${user.email}'),
        RaisedButton(
          child: Text('Sign out'),
          onPressed: logout,
        ),
      ],
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
    );
  }
}