import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';
import { MapView, Constants, Location, Permissions } from 'expo';
import { SearchBar } from 'react-native-elements'



export default class App extends React.Component {
  constructor(props) {
    super(props)

    let initregion = {
      latitude: 34.052235,
      longitude: -118.243685,
      latitudeDelta: 0.692,
      longitudeDelta: 0.0821,
    }

    this.state = {
      initregion,
    }

  }

  componentWillMount() {
    this._getLocationAsync();
  }

  _getLocationAsync = async () => {
    let { status } = await Permissions.askAsync(Permissions.LOCATION);
    if (status !== 'granted') {
      this.setState({
        errorMessage: 'Permission to access location was denied',
      });
    }

    let location = await Location.getCurrentPositionAsync({});
    console.log(location);
    this.setState({
      initregion: {
        latitude: location.coords.latitude,
        longitude: location.coords.longitude,
        latitudeDelta: 0.072,
        longitudeDelta: 0.0021,
      }
    });
  };

  render() {
    return (
      <View style={styles.container}>
        <View style={{ justifyContent: 'center', alignItems: 'center' }}>
          <Text style={{ marginTop: '10%', fontSize: 17, fontWeight: 'bold', color: '#f8971d' }}> Parking Buddy </Text>
        </View>
        {/* <TextInput
          style={{
            height: 40, borderColor: 'gray', borderWidth: 1, borderRadius: 10, padding: 10, width: '100%',
            marginTop: 10, justifyContent: 'center', alignContent: 'center', overflow: 'hidden'
          }}
          onChangeText={(text) => this.setState({ inputUsername: text })}
          placeholder='Search'
        /> */}


        <View style={{ flex: 1 }}>
          <MapView
            ref={component => this._MapView = component}

            style={{ flex: 1 }}
            initialRegion={this.state.initregion}
            showsUserLocation={true}

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
