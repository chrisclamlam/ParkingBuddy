import React from 'react';
import { StyleSheet, Text, View, TextInput, Image } from 'react-native';
import { MapView } from 'expo';
import { FormLabel, FormInput, Button } from 'react-native-elements'
import { Ionicons } from '@expo/vector-icons';

export default class ProfileScreen extends React.Component {
  constructor(props) {
    super(props)

  }



  render() {
    return (
      <View style={styles.container}>
        <Text onPress= {() => this.props.navigation.pop()}> back </Text>

        <Text> Hello Name!</Text>
      </View >
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    // alignItems: 'center',
    justifyContent: 'center',
  },
});
