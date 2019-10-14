import 'package:flutter/material.dart';

Widget pad(Widget child, { double padding = 16.0 }) {
  return Container(
    child: child,
    padding: EdgeInsets.all(padding),
  );
}

Widget loader(bool loading, Widget child) {
  if (!loading) return child;
  return Stack(
    children: [
      Container(child: child, decoration: BoxDecoration(color: Color(0x88000000))),
      Center(child: CircularProgressIndicator()),
    ],
  );
}