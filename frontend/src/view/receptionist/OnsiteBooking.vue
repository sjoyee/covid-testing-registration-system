<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="backToMain">
        <v-icon left>mdi-arrow-left-bold</v-icon>
        BACK TO MAIN
      </v-btn>
      <v-btn text @click="search">
        <v-icon left>mdi-map-search</v-icon>
        SEARCH TESTING SITES
      </v-btn>
      <v-btn text @click="logout">LOG OUT</v-btn>
    </v-toolbar>
    <div class="ma-4">
      <div class="text-h5 font-weight-bold pa-4">On-Site Booking</div>
      <v-col md="8">
        <v-card class="ma-4 pa-4" tile>
          <v-card-title>Customer Details</v-card-title>
          <div class="pa-4">
            <v-form ref="customerForm" v-model="valid">
              <v-row>
                <v-col md="6">
                  <v-text-field
                    v-model="givenName"
                    label="Given Name"
                    :rules="mandatoryRule"
                  ></v-text-field>
                </v-col>
                <v-col md="6">
                  <v-text-field
                    v-model="familyName"
                    label="Family Name"
                    :rules="mandatoryRule"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col md="6">
                  <v-text-field
                    v-model="userName"
                    label="Username"
                    :rules="mandatoryRule"
                  ></v-text-field>
                </v-col>
                <v-col md="6">
                  <v-text-field
                    v-model="password"
                    label="Password"
                    type="password"
                    :rules="mandatoryRule"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col md="6">
                  <v-text-field
                    v-model="phoneNumber"
                    label="Phone Number"
                    :rules="mandatoryRule"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row class="pa-2"><div>Select the date and time:</div></v-row>
              <v-row>
                <v-date-picker
                  v-model="date"
                  :min="moment().format()"
                  class="pa-2"
                ></v-date-picker>
                <v-time-picker
                  v-model="time"
                  format="24hr"
                  class="pa-2"
                ></v-time-picker>
              </v-row>
            </v-form>
          </div>
          <v-card-actions class="pa-4">
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid" @click="book">BOOK</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </div>
    <div class="text-h6 pa-4 mb-12">Generated Booking ID: {{ bookingId }}</div>
    <v-snackbar v-model="errorMessage" :timeout="timeout" color="error">
      Fail to initiate on-site booking. Please try again.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="errorMessage = false"> Close </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar v-model="successMessage" :timeout="timeout" color="success">
      Successfully initiated on-site booking and sent PIN code to customer.
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      customerForm: null,
      valid: false,
      givenName: "",
      familyName: "",
      userName: "",
      password: "",
      phoneNumber: "",
      customerDetails: null,
      errorMessage: false,
      successMessage: false,
      timeout: 2000,
      date: "",
      time: "",
      bookingId: "",
    };
  },
  methods: {
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/search`,
      });
    },
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/`,
      });
    },
    async logout() {
      await this.$router.push({
        path: "/login",
      });
    },
    async book() {
      try {
        const res = await axios.post(`/${this.$route.params.id}/booking`, {
          givenName: this.givenName,
          familyName: this.familyName,
          userName: this.userName,
          password: this.password,
          phoneNumber: this.phoneNumber,
          startTime: this.date + "T" + this.time + "Z",
          isHomeBooking: false,
        });
        this.successMessage = true;
        this.bookingId = res.data;
      } catch {
        // handle error
        console.log("Fail to create an on-site booking.");
        this.errorMessage = true;
      }
      this.$refs.customerForm.reset();
    },
  },
  computed: {
    mandatoryRule() {
      return [(v) => !!v || "This field is mandatory."];
    },
  },
};
</script>

<style></style>
