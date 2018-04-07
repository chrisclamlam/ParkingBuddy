import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';
import { MapView } from 'expo';

export default class App extends React.Component {
  render() {
    return (
      <View style={styles.container}>
        <View style={{justifyContent:'center', alignItems:'center'}}>
          <Text style={{ marginTop: '10%', fontSize:17, fontWeight: 'bold' }}> PARKING BUDDY </Text>
        </View>
        <TextInput
          style={{
            height: 40, borderColor: 'gray', borderWidth: 1, borderRadius: 10, padding: 10, width: '100%',
            marginTop: 10, justifyContent: 'center', alignContent: 'center', overflow: 'hidden'
          }}
          onChangeText={(text) => this.setState({ inputUsername: text })}
          placeholder='Search'
        />
        <View style={{ flex: 1 }}>
          <MapView
            style={{ flex: 1 }}
            initialRegion={{
              latitude: 37.78825,
              longitude: -122.4324,
              latitudeDelta: 0.0922,
              longitudeDelta: 0.0421,
            }}
          />
        </View>
      </View >
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
});
