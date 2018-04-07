import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';
import { MapView } from 'expo';

export default class SignUpScreen extends React.Component {
  render() {
    return (
      <View style={styles.container}>
       <Text> SIGN UP SCREEN </Text>




       <Text onPress={() => this.props.navigation.pop()}> REGISTER TAKES BACK TO LOGIN PAGE</Text>



       
      </View >
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
