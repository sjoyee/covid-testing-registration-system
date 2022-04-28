<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="search">
        <v-icon left>mdi-map-search</v-icon>
        SEARCH TESTING SITES
      </v-btn>
      <v-btn text @click="logout">LOG OUT</v-btn>
    </v-toolbar>
    <div class="ma-4">
      <div class="text-h5 font-weight-bold pa-4">Home Booking</div>
      <div class="text-subtitle-1 pa-4">Please choose the relevant option.</div>
      <v-col md="8">
        <v-card class="ma-4 pa-4" tile>
          <v-form ref="homeBookingForm">
            <div class="text-subtitle-1 font-weight-bold">
              Please confirm that you are booking for home testing.
            </div>
            <div class="pa-4">
              <v-row>
                <v-checkbox
                  label="Yes, I confirm that I am booking for home testing."
                  v-model="isHomeBooking"
                ></v-checkbox>
              </v-row>
            </div>
            <div class="text-subtitle-1 font-weight-bold">
              Do you need the RAT kit?
            </div>
            <div class="pa-4">
              <v-row>
                <v-radio-group v-model="hasRatKit" column mandatory>
                  <v-radio
                    label="Yes, I need the RAT kit."
                    :value="false"
                  ></v-radio>
                  <v-radio
                    label="No, I do not need the RAT kit."
                    :value="true"
                  ></v-radio>
                </v-radio-group>
              </v-row>
            </div>
          </v-form>
          <v-card-actions class="pa-4">
            <v-spacer></v-spacer>
            <v-btn :disabled="!isHomeBooking" @click="book">BOOK</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </div>
    <v-snackbar v-model="errorMessage" :timeout="timeout" color="error">
      Fail to create home booking. Please try again.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="errorMessage = false"> Close </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar v-model="successMessage" :timeout="timeout" color="success">
      Successfully created home booking and sent QR Code and video conferencing
      URL.
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      homeBookingForm: null,
      hasRatKit: false,
      isHomeBooking: false,
      errorMessage: false,
      successMessage: false,
      timeout: 2000,
    };
  },
  methods: {
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/search`,
      });
    },

    async book() {
      try {
        await axios.post("/user/booking", {
          isHomeBooking: this.isHomeBooking,
          hasRatKit: this.hasRatKit,
        });
        this.successMessage = true;
      } catch {
        // handle error
        console.log("Fail to create home booking.");
        this.errorMessage = true;
      }
      this.$refs.homeBookingForm.reset();
    },

    logout() {
      this.$router.push({
        path: "/login",
      });
    },
  },
};
</script>

<style></style>
