<template>
  <div>
    <v-dialog v-model="dialog" width="700" :retain-focus="false">
      <v-card class="px-4 py-2">
        <div class="text-h6 font-weight-bold pa-4">Modify Booking</div>
        <v-form ref="bookingForm">
          <div class="pa-4">
            <div class="text-subtitle-1">
              Booking ID: {{ bookingDetails.id }}
            </div>
          </div>
          <div class="pa-4">
            <v-select
              v-model="bookingDetailsCopy.testingSite"
              label="Testing Sites"
              :items="testingSites"
              item-text="name"
              item-value="id"
              return-object
            ></v-select>
          </div>
          <div class="pa-4">
            <div class="text-subtitle-1">Datetime:</div>
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
          </div>
        </v-form>
        <v-card-actions>
          <v-btn @click="dialog = false">Cancel</v-btn>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            @click="
              modify(
                bookingDetails.id,
                bookingDetailsCopy.testingSite.id,
                date,
                time
              )
            "
            >Modify</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
    <!-- <v-snackbar v-model="errorMessage" :timeout="timeout" color="error">
      Fail to modify booking.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="errorMessage = false"> Close </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar v-model="successMessage" :timeout="timeout" color="success">
      Successfully modify booking.
    </v-snackbar> -->
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
export default {
  props: {
    value: {
      type: Boolean,
      default: false,
    },

    bookingDetails: {
      type: Object,
      default() {
        return {};
      },
    },

    testingSites: {
      type: Array,
      default() {
        return [];
      },
    },
  },

  mounted() {
    if (this.bookingDetailsCopy.startTime) {
      this.formatDate(this.bookingDetailsCopy.startTime);
      this.formatTime(this.bookingDetailsCopy.startTime);
    }
  },

  data() {
    return {
      bookingDetailsCopy: { ...this.bookingDetails },
      successMessage: false,
      errorMessage: false,
      timeout: 2000,
      date: "",
      time: "",
    };
  },

  computed: {
    dialog: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit("input", val);
      },
    },
  },

  watch: {
    dialog() {
      this.formatDate(this.bookingDetailsCopy.startTime);
      this.formatTime(this.bookingDetailsCopy.startTime);
    },
    bookingDetails(val) {
      this.bookingDetailsCopy = { ...val };
      if (this.bookingDetailsCopy.startTime) {
        this.formatDate(this.bookingDetailsCopy.startTime);
        this.formatTime(this.bookingDetailsCopy.startTime);
      }
    },
  },

  methods: {
    async modify(bookingId, testingSiteId, date, time) {
      let dateTime = date + "T" + time + "Z";
      console.log(testingSiteId);
      console.log(dateTime);
      try {
        await axios.post(
          `/${this.$route.params.id}/booking/active-booking/modify?bookingId=${bookingId}&testingSiteId=${testingSiteId}&dateTime=${dateTime}`
        );
        this.successMessage = true;
        this.formatDate(this.bookingDetailsCopy.startTime);
        this.formatTime(this.bookingDetailsCopy.startTime);
        this.dialog = false;
      } catch (e) {
        // handle error
        console.log(e);
        console.log("Fail to modify booking.");
        this.errorMessage = true;
      }
    },
    formatDate(datetime) {
      this.date = moment(datetime, "YYYY-MM-DDTHH:mm:ss").format("YYYY-MM-DD");
      console.log(this.date);
    },
    formatTime(datetime) {
      this.time = moment(datetime, "YYYY-MM-DDTHH:mm:ss").format("kk:mm:ss");
      console.log("helo");
      console.log(this.time);
    },
  },
};
</script>

<style></style>
