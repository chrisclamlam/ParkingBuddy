import React from 'react';
import { StyleSheet, Text, View, TextInput, Animated, Dimensions, Image, FlatList, } from 'react-native';
import { MapView, Constants, Location, Permissions } from 'expo';
import { List, ListItem, FormLabel, FormInput, Button, } from 'react-native-elements'
import { Marker } from 'react-native-maps';
// import ControlBanner from './ControlBannerHeader'

// Get the dimensions of the screen
const { width, height } = Dimensions.get("window");
// Link to marker background
const Images = "https://i.imgur.com/sNam9iJ.jpg";
// Make the dimensions of the parking quick detail screen
const PARK_HEIGHT = height / 4;
const PARK_WIDTH = PARK_HEIGHT - 50;

export default class App extends React.Component {
    constructor(props) {
        super(props)

        // Set an initial region, for when user location cannot be determined
        let initregion = {
            latitude: 34.052235,
            longitude: -118.243685,
            latitudeDelta: 0.692,
            longitudeDelta: 0.0821,
        };

        // Set state variables
        this.state = {
            initregion,
            // Create array for map markers
            markers: [
                {
                    coordinate : {
                        latitude: 37.421,
                        longitude: -122.084,
                    },
                    title: 'Marker 1',
                    description: "Test 1",
                },
                {
                    coordinate : {
                        latitude: 38.421,
                        longitude: -123.084,
                    },
                    title: 'Marker 2',
                    description: "Test 2",
                },
                {
                    coordinate : {
                        latitude: 36.421,
                        longitude: -121.084,
                    },
                    title: 'Marker 3',
                    description: "Test 3",
                },
            ],
            searchQuery: "",
        }


    }
    // To set up animation and default index
    componentWillMount() {
        this.index = 0;
        this.animation = new Animated.Value(0);
    }

   
  
  // Move map to map markers position
  animateTo(latitude, longitude) {
    this.compo.animateToRegion({ latitude, longitude })
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
        <View style={{ justifyContent: 'center', alignItems: 'center', height: '10%' }}>
          <Text style={{ marginTop: '10%', fontSize: 17, fontWeight: 'bold', color: '#f8971d' }}> Parking Buddy </Text>
          <Text onPress={() => this.props.navigation.push('ProfileScreen')
          }> User Profile </Text>
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
            zoomEnabled={true}
            scrollEnabled={true}
            rotateEnabled={true}
          />

          {this.state.markers.map((marker, index) => {
            return (
              <MapView.Marker key={index} coordinate={marker.coordinate} onLoad={() => this.animateTo(marker.coordinate)}>
                <Animated.View style={[styles.markerWrap]}>
                  <Animated.View style={[styles.ring]} />
                  <View style={styles.marker} />
                </Animated.View>
              </MapView.Marker>
            );
          })}
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
  markerWrap: {
    alignItems: "center",
    justifyContent: "center",
  },
  marker: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: "rgba(130,4,150, 0.9)",
  },
  ring: {
    width: 24,
    height: 24,
    borderRadius: 12,
    backgroundColor: "rgba(130,4,150, 0.3)",
    position: "absolute",
    borderWidth: 1,
    borderColor: "rgba(130,4,150, 0.5)",
  },
});
