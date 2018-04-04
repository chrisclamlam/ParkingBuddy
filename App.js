import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';
import StackScreen from './Components/StackScreen.js'

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
