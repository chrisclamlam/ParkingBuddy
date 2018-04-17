import React from 'react';
import { StyleSheet, Text, View, TextInput, Animated, Dimensions, Image, FlatList, Alert} from 'react-native';
import { MapView, Constants, Location, Permissions } from 'expo';
import { List, ListItem, FormLabel, FormInput, Button, } from 'react-native-elements'
import { Marker } from 'react-native-maps';

// Get the dimensions of the screen
const { width, height } = Dimensions.get("window");
// Link to marker background
// Make the dimensions of the parking quick detail screen
const PARK_HEIGHT = height / 6;
const PARK_WIDTH = PARK_HEIGHT;

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
            markers: this.props.navigation.state.params.markers,
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
                            latitudeDelta: this.state.initregion.latitudeDelta,
                            longitudeDelta: this.state.initregion.longitudeDelta,
                        },
                        350
                    );
                }
            }, 10);
        });
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
                {/* Search for an area with parking */}
                <View style={{ flex: 1 }}>
                    <MapView
                        ref={map => this.map = map}
                        provider="google"
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
                            <View style={styles.park} key={index}>
                                <View style={styles.textContent}>
                                    <Text numberOfLines={1} style={styles.parktitle}>{marker.title}</Text>
                                    <Text numberOfLines={1} style={styles.parkDescription}>
                                        {marker.description}
                                    </Text>
                                </View>
                            </View>
                        ))}
                    </Animated.ScrollView>
                    <View style={{flexDirection: 'row', justifyContent: 'center', padding: 2}}>
                        <Button
                            buttonStyle={{ borderRadius: 10, backgroundColor: '#f8971d', }}
                            onPress={() => this.props.navigation.pop()} title="Go Back" />

                        <Button
                            buttonStyle={{ disabled: global.loggedIn, borderRadius: 10, backgroundColor: '#f8971d',  }}
                            onPress={() => this.props.navigation.push('AddSpotScreen')} title="Don't see your spot?"
                            disabled={global.loggedIn} />
                    </View>
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
        bottom: 100,
        left: 10,
        right: 0,
        paddingVertical: 10,
    },
    endPadding: {
        paddingRight: width - PARK_WIDTH,
    },
    park: {
        padding: 5,
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
        position: "absolute",
        top: 0,
        flex: 1,
        alignSelf: "center",

    },
    parktitle: {
        alignSelf: "center",
        fontSize: 12,
        marginTop: 5,
        fontWeight: "bold",
    },
    parkDescription: {
        alignSelf: "center",
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
