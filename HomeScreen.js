import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';
import { StackNavigator } from 'react-navigation'

export default class HomeScreen extends React.Component {
  constructor(props) {
    super(props)

   this.state = {
     input
   }
  }
  render() {
    return (
      <View style={styles.container}>
        <Text>Where would you like to go?</Text>
        <View style={{ width: 200 }}>
          <TextInput
            style={{ height: 40, borderColor: 'gray', borderWidth: 1, borderRadius: 10, padding: 10 }}
            onChangeText={(text) => this.setState({ input: text })}
            onSubmitEditing={() => this.props.navigation.push('MapScreen', {input})}
          />
        </View>
      </View>
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
