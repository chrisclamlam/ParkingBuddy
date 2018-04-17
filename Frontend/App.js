import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';
import StackScreen from './Components/StackScreen.js'

// Global variable for IP -> to use when fetching data from server
global.serverIP = "http://172.20.10.4:8080/ParkingBuddy/";
global.authKey = ""; // To hold auth key
global.loggedIn = false; // To check if user is logged in
global.username = "guest";
global.signedUp = false;



export default class App extends React.Component {
  render() {
    return (
      <StackScreen />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
