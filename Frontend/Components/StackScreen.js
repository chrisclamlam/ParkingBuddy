import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { StackNavigator } from 'react-navigation'


import HomeScreen from './HomeScreen';
import MapScreen from './MapScreen';
import LogInScreen from './LogInScreen';

import SignUpScreen from './SignUpScreen';
import ProfileScreen from './ProfileScreen'


const AppStackNavigator = StackNavigator({
  LogInScreen: {
    screen: LogInScreen
  },
  SignUpScreen: {
    screen: SignUpScreen
  },
  MapScreen: {
    screen: MapScreen
  },
  ProfileScreen: {
    screen: ProfileScreen
  }
}, {
    headerMode: "none",
  })

class StackScreen extends React.Component {
  render() {
    return (
      <AppStackNavigator />
    );
  }
}

export default StackScreen;