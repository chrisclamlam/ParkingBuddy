import React from 'react';
import { StyleSheet, Text, View, TextInput, Animated, Dimensions, Image, } from 'react-native';
import { MapView, Constants, Location, Permissions } from 'expo';
import { List, ListItem, FormLabel, FormInput, Button, } from 'react-native-elements'
import { Marker } from 'react-native-maps';

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

    // On marker load
    componentDidMount() {
        // We should detect when scrolling has stopped then animate
        // We should just debounce the event listener here
        this.animation.addListener(({ value }) => {
            let index = Math.floor(value / PARK_WIDTH + 0.3); // animate 30% away from landing on the next item
            if (index >= this.state.markers.length) {
                index = this.state.markers.length - 1;
            }
            if (index <= 0) {
                index = 0;
            }

            clearTimeout(this.regionTimeout);
            this.regionTimeout = setTimeout(() => {
                if (this.index !== index) {
                    this.index = index;
                    const { coordinate } = this.state.markers[index];
                    this.map.animateToRegion(
                        {
                            ...coordinate,
                            latitudeDelta: this.state.region.latitudeDelta,
                            longitudeDelta: this.state.region.longitudeDelta,
                        },
                        350
                    );
                }
            }, 10);
        });
    }

    // Move map to map markers position
    animateTo(latitude, longitude) {
        this.compo.animateToRegion({ latitude, longitude })
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
                latitudeDelta: 0.092,
                longitudeDelta: 0.0221,
            }
        });
    };

    render() {
        const interpolations = this.state.markers.map((marker, index) => {
            const inputRange = [
                (index - 1) * PARK_WIDTH,
                index * PARK_WIDTH,
                ((index + 1) * PARK_WIDTH),
            ];
            const scale = this.animation.interpolate({
                inputRange,
                outputRange: [1, 2.5, 1],
                extrapolate: "clamp",
            });
            const opacity = this.animation.interpolate({
                inputRange,
                outputRange: [0.35, 1, 0.35],
                extrapolate: "clamp",
            });
            return { scale, opacity };
        });
        return (
            <View style={styles.container}>
                {/*<View style={{ justifyContent: 'center', alignItems: 'center' }}>*/}
                {/*<Text style={{ marginTop: '10%', fontSize: 17, fontWeight: 'bold' }}> PARKING BUDDY </Text>*/}
                {/*</View>*/}
                {/*<TextInput*/}
                {/*style={{*/}
                {/*height: 40, borderColor: 'gray', borderWidth: 1, borderRadius: 10, padding: 10, width: '100%',*/}
                {/*marginTop: 10, justifyContent: 'center', alignContent: 'center', overflow: 'hidden'*/}
                {/*}}*/}
                {/*onChangeText={(text) => this.setState({ inputUsername: text })}*/}
                {/*placeholder='Search'*/}
                {/*/>*/}
                <FormLabel>Parking Search</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ searchQuery: text }))} />
                <View style={{ flex: 1 }}>
                    <MapView
                        ref={map => this.map = map}

                        style={{ flex: 1 }}
                        initialRegion={this.state.initregion}
                        showsUserLocation={true}
                        zoomEnabled={true}
                        scrollEnabled={true}
                        rotateEnabled={true}
                    >

                        {this.state.markers.map((marker, index) => {
                            const scaleStyle = {
                                transform: [
                                    {
                                        scale: interpolations[index].scale,
                                    },
                                ],
                            };
                            const opacityStyle = {
                                opacity: interpolations[index].opacity,
                            };
                            return (
                                <MapView.Marker key={index} coordinate={marker.coordinate} >
                                    <Animated.View style={[styles.markerWrap, opacityStyle]}>
                                        <Animated.View style={[styles.ring, scaleStyle]} />
                                        <View style={styles.marker} />
                                    </Animated.View>
                                </MapView.Marker>
                            );
                        })}
                    </MapView>

                    { /* To scroll through results of parking spots and display on screen */ }
                    <Animated.ScrollView
                        horizontal
                        scrollEventThrottle={1}
                        showsHorizontalScrollIndicator={false}
                        snapToInterval={PARK_WIDTH}
                        onScroll={Animated.event(
                            [
                                {
                                    nativeEvent: {
                                        contentOffset: {
                                            x: this.animation,
                                        },
                                    },
                                },
                            ],
                            { useNativeDriver: true }
                        )}
                        style={styles.scrollView}
                        contentContainerStyle={styles.endPadding}
                    >

                        { /* Dynamically display results of parking locations on screen */ }
                        {this.state.markers.map((marker, index) => (
                            <View style={styles.card} key={index}>
                                <Image
                                    style={styles.parkImage}
                                    source={{uri: '../images/park.bmp'}}
                                    resizeMode="cover"
                                />
                                <View style={styles.textContent}>
                                    <Text numberOfLines={1} style={styles.parktitle}>{marker.title}</Text>
                                    <Text numberOfLines={1} style={styles.parkDescription}>
                                        {marker.description}
                                    </Text>
                                </View>
                            </View>
                        ))}
                    </Animated.ScrollView>
                    <Button
                        buttonStyle={{ borderRadius: 10, backgroundColor: 'rgb(76,217,100)', width: '100%' }}
                        onPress={() => this.verifyInput(this)} title="Add a Custom Spot" />
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
    scrollView: {
        position: "absolute",
        bottom: 30,
        left: 0,
        right: 0,
        paddingVertical: 10,
    },
    endPadding: {
        paddingRight: width - PARK_WIDTH,
    },
    park: {
        padding: 10,
        elevation: 2,
        backgroundColor: "#FFF",
        marginHorizontal: 10,
        shadowColor: "#000",
        shadowRadius: 5,
        shadowOpacity: 0.3,
        shadowOffset: { x: 2, y: -2 },
        height: PARK_HEIGHT,
        width: PARK_WIDTH,
        overflow: "hidden",
    },
    parkImage: {
        flex: 3,
        width: "100%",
        height: "100%",
        alignSelf: "center",
    },
    textContent: {
        flex: 1,
    },
    parktitle: {
        fontSize: 12,
        marginTop: 5,
        fontWeight: "bold",
    },
    parkDescription: {
        fontSize: 12,
        color: "#444",
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
