import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { StackNavigator } from 'react-navigation'


import HomeScreen from './HomeScreen';
import MapScreen from './MapScreen';
import LogInScreen from './LogInScreen';
import AddSpotScreen from './AddSpotScreen';
import SignUpScreen from './SignUpScreen';
import SearchLocationScreen from './SearchLocationScreen';
import ProfileScreen from './ProfileScreen'
import FriendsScreen from './FriendsScreen'

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
  AddSpotScreen: {
    screen: AddSpotScreen
  },
  SearchLocationScreen: {
    screen: SearchLocationScreen
  },
  ProfileScreen: {
    screen: ProfileScreen
  },
  FriendsScreen :{
    screen: FriendsScreen
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