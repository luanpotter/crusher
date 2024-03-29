import 'package:flutter/material.dart';

import 'login.dart';
import 'pages/login_page.dart';
import 'pages/main_page.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Crusher',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  bool loading = true;
  Login user;

  @override
  void initState() {
    super.initState();
    Login.silentLogin().then((user) => this.setState(() {
      this.loading = false;
      this.user = user;
    }));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Crusher'),
      ),
      body: buildBody(),
    );
  }

  Widget buildBody() {
    if (loading) {
      return Center(child: CircularProgressIndicator());
    } else if (user == null) {
      return LoginPage(callback: (user) => this.setState(() => this.user = user));
    } else {
      return MainPage(user: user, logout: () async {
        this.setState(() {
          this.loading = true;
          this.user = null;
        });
        await Login.signOut();
        this.setState(() => this.loading = false);
      });
    }
  }
}
