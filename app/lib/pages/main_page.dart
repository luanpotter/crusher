import 'package:flutter/material.dart';

import '../login.dart';

class MainPage extends StatelessWidget {
  final Login user;
  
  const MainPage({Key key, this.user}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Text('Logged in: ${user.email}'),
    );
  }
}