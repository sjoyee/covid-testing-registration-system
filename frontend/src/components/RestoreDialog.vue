<template>
  <div>
    <v-dialog v-model="dialog" width="700" :retain-focus="false">
      <v-card class="px-4 py-2">
        <div class="text-h6 font-weight-bold pa-4">Restore Booking</div>
        <v-form ref="bookingForm">
          <div class="pa-4">
            <div class="text-subtitle-1">
              Booking ID: {{ bookingDetails.id }}
            </div>
          </div>
          <v-card flat tile v-if="bookingDetailsCopy">
            <v-list flat>
              <v-list-item-group>
                <template v-for="history in bookingDetailsCopy.histories">
                  <v-list-item :key="history.id">
                    <v-list-item-content>
                      <v-card class="ma-4 pa-4">
                        <div class="text-subtitle-1 pa-2">
                          <div>
                            Updated At: {{ formatDateTime(history.updatedAt) }}
                          </div>
                          <div>
                            Testing Site:
                            {{ getSiteName(history.testingSiteId) }}
                          </div>
                          <div>
                            Datetime: {{ formatDateTime(history.dateTime) }}
                          </div>
                        </div>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                            color="primary"
                            @click="
                              restore(
                                bookingDetails.id,
                                history.testingSiteId,
                                history.dateTime
                              )
                            "
                            >Restore</v-btn
                          >
                        </v-card-actions>
                      </v-card>
                    </v-list-item-content>
                  </v-list-item>
                </template>
              </v-list-item-group>
            </v-list>
            <v-card-actions>
              <v-btn @click="dialog = false">Cancel</v-btn>
            </v-card-actions>
          </v-card>
        </v-form>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="errorMessage" :timeout="timeout" color="error">
      Fail to modify booking.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="errorMessage = false"> Close </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar v-model="successMessage" :timeout="timeout" color="success">
      Successfully modify booking.
    </v-snackbar>
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

  mounted() {},

  data() {
    return {
      bookingDetailsCopy: { ...this.bookingDetails },
      successMessage: false,
      errorMessage: false,
      timeout: 2000,
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
    bookingDetails(val) {
      this.bookingDetailsCopy = { ...val };
    },
  },

  methods: {
    async restore(bookingId, testingSiteId, dateTime) {
      try {
        await axios.post(
          `/${this.$route.params.id}/booking/active-booking/restore?bookingId=${bookingId}&testingSiteId=${testingSiteId}&dateTime=${dateTime}`
        );
        this.successMessage = true;
        this.dialog = false;
      } catch {
        // handle error
        console.log("Fail to restore booking.");
        this.errorMessage = true;
      }
    },
    formatDateTime(datetime) {
      return moment(datetime, "YYYY-MM-DDTHH:mm:ss:SSZ").format(
        "YYYY-MM-DD kk:mm:ss"
      );
    },
    getSiteName(id) {
      for (var site of this.testingSites) {
        if (site.id == id) {
          return site.name;
        }
      }
    },
  },
};
</script>

<style></style>
