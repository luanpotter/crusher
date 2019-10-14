import 'package:google_sign_in/google_sign_in.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter_auth_buttons/flutter_auth_buttons.dart';
import 'package:flutter/material.dart' show VoidCallback;

class Login {
  static final GoogleSignIn _googleSignIn = GoogleSignIn();
  static final FirebaseAuth _auth = FirebaseAuth.instance;

  String email;

  Login(FirebaseUser user) {
    this.email = user.email;
  }

  static Future<Login> silentLogin() async {
    final FirebaseUser currentUser = await _auth.currentUser();
    if (currentUser != null && currentUser.isEmailVerified) {
      return Login(currentUser);
    }
    return null;
  }

  static Future<Login> doSignIn() async {
    final GoogleSignInAccount googleUser = await _googleSignIn.signIn();
    final GoogleSignInAuthentication googleAuth = await googleUser.authentication;

    final AuthCredential credential = GoogleAuthProvider.getCredential(
      accessToken: googleAuth.accessToken,
      idToken: googleAuth.idToken,
    );

    final FirebaseUser user = (await _auth.signInWithCredential(credential)).user;
    if (!user.isEmailVerified) throw 'Invalid account, email verification required';
    return Login(user);
  }

  static Future<void> signOut() {
    return _auth.signOut();
  }

  static GoogleSignInButton button(VoidCallback onPressed) {
    return GoogleSignInButton(onPressed: onPressed);
  }
}
