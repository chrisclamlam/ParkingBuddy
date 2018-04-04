import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { StackNavigator } from 'react-navigation'


import HomeScreen from './HomeScreen';
import MapScreen from './MapScreen';



const AppStackNavigator = StackNavigator({
  HomeScreen: {
    screen: HomeScreen,
  },
  MapScreen: {
    screen: MapScreen
  },
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