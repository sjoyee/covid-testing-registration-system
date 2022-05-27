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
    <div class="text-h5 font-weight-bold pa-4">Modify Active Booking</div>
    <div class="text-h6 pa-4">Search Active Booking</div>
    <v-row v-if="successFound">
      <v-card flat tile class="ma-4" v-if="bookingDetails">
        <v-list-item :key="bookingDetails.id">
          <v-list-item-content>
            <v-card class="ma-4 pa-4">
              <div class="text-subtitle-1 pa-2">
                <div>Booking ID: {{ bookingDetails.id }}</div>
                <div>Testing Site: {{ bookingDetails.testingSite.name }}</div>
                <div>
                  Datetime: {{ formatDateTime(bookingDetails.startTime) }}
                </div>
              </div>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text @click="modify(bookingDetails)">Modify</v-btn>
                <v-btn text @click="restore(bookingDetails)" color="primary"
                  >Restore</v-btn
                >
                <v-btn text @click="cancel(bookingDetails.id)" color="error"
                  >Cancel</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-list-item-content>
        </v-list-item>

        <modify-dialog
          v-if="bookingDetails"
          v-model="openModifyDialog"
          :bookingDetails="bookingDetails"
          :testingSites="testingSites"
        ></modify-dialog>

        <restore-dialog
          v-if="bookingDetails"
          v-model="openRestoreDialog"
          :bookingDetails="bookingDetails"
          :testingSites="testingSites"
        ></restore-dialog>
        <v-snackbar v-model="errorCancel" :timeout="timeout" color="error">
          Fail to cancel booking.
          <template v-slot:action="{ attrs }">
            <v-btn text v-bind="attrs" @click="errorMessage = false">
              Close
            </v-btn>
          </template>
        </v-snackbar>
        <v-snackbar v-model="successCancel" :timeout="timeout" color="success">
          Successfully cancel booking.
        </v-snackbar>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="successFound = !successFound"> BACK </v-btn>
        </v-card-actions>
      </v-card>
    </v-row>
    <div v-else>
      <div class="text-subtitle-1 px-4">
        Please provide booking ID to perform modification on the booking.
      </div>
      <v-col md="6">
        <v-card class="ma-4 pa-4" tile>
          <v-card-subtitle>Please provide booking ID.</v-card-subtitle>
          <v-form ref="form">
            <v-row class="pa-4">
              <v-col>
                <v-text-field
                  v-model="bookingId"
                  label="Booking ID"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-card-actions class="pa-4">
              <v-spacer></v-spacer>
              <v-btn @click="getActiveBookingById">CONFIRM</v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-col>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import ModifyDialog from "@/components/ModifyDialog.vue";
import RestoreDialog from "@/components/RestoreDialog.vue";

export default {
  components: { ModifyDialog, RestoreDialog },
  data() {
    return {
      bookingForm: null,
      bookingDetails: null,
      successCancel: false,
      errorCancel: false,
      timeout: 2000,
      openModifyDialog: false,
      openRestoreDialog: false,
      activeBooking: null,
      sites: null,
      smsPin: "",
      bookingId: "",
      successFound: false,
    };
  },
  mounted() {
    this.filterTestingSites();
  },

  methods: {
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/`,
      });
    },
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/customer/search`,
      });
    },

    async getActiveBookingById() {
      try {
        const response = await axios.get(
          `/${this.$route.params.id}/booking/active-booking?id=${this.bookingId}`
        );
        this.bookingDetails = response.data;
        this.successFound = true;
      } catch {
        console.log("Fail to get active booking.");
      }
      this.$refs.form.reset();
    },

    modify(booking) {
      this.bookingDetails = booking;
      this.openModifyDialog = true;
    },

    restore(booking) {
      this.bookingDetails = booking;
      this.openRestoreDialog = true;
    },

    async cancel(id) {
      try {
        await axios.post(
          `/${this.$route.params.id}/booking/active-booking/cancel?id=${id}`
        );
        this.successCancel = true;
        this.getActiveBookings();
      } catch {
        // handle error
        console.log("Fail to cancel booking.");
        this.errorCancel = true;
      }
    },

    async filterTestingSites() {
      try {
        const response = await axios.get(`/testing-site?type=&suburb=`);
        this.sites = response.data;
      } catch {
        console.log("Fail to get testing sites.");
      }
    },

    async logout() {
      await this.$router.push({
        path: "/login",
      });
    },
    formatDateTime(datetime) {
      return moment(datetime, "YYYY-MM-DDTHH:mm:ssZ").format(
        "DD-MM-YYYY HH:mm"
      );
    },
  },
  computed: {
    testingSites() {
      var testingSites = [];
      for (var item of this.sites) {
        testingSites.push({
          name: item.name,
          id: item.id,
        });
      }
      return testingSites;
    },
  },
};
</script>

<style></style>
