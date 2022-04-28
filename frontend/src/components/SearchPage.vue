<template>

  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <div v-if="isLoggedIn">
        <v-btn text @click="backToMain">
          <v-icon left>mdi-arrow-left-bold</v-icon>
          BACK TO MAIN
        </v-btn>
      </div>
      <v-btn v-else text @click="login">LOGIN</v-btn>
    </v-toolbar>
    <v-card class="justify-center pt-12" flat tile>
      <v-row class="justify-center ma-12" flat tile>
        <h1>Testing Sites</h1>
      </v-row>
      <section class="dropdown-wrapper">
        <div @click="isVisible = !isVisible" class="selected-item">
        <span>Select Facility</span>
        <svg class="dropdown-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"/><path d="M12 10.828l-4.95 4.95-1.414-1.414L12 8l6.364 6.364-1.414 1.414z"/></svg>
      </div>
      <v-row class="justify-center">
        <div v-if="isVisible" class="dropdown-popover">
          <input type="text" v-model="searchQuery" placeholder=" Search by Facility Type" >
        <div class="options">
            <ul>
              <li>Drive Through</li>
              <li>Walk in</li>
              <li>Clinics</li>
              <li>GPs</li>
              <li>Hospitals</li>
            </ul>
          </div>
          <input type="text" v-model="searchQuery" placeholder=" Search by Suburb" >
        <div class="options">
            <ul>
              <li>Drive Through</li>
              
            </ul>
          </div>
        </div>
          </v-row>
          <v-row class="ma-10 " flat tile>
        <Table></Table>
      </v-row>
        
      
      </section>
      
          </v-card>
           
        
      
           
       
       
          
  </div>
</template>

<script>
import Table from './Table.vue';

export default {
  components:{
    Table
  },
  data() {
    return {
      isLoggedIn: false,
      searchQuery:'',
      selectedItem: null,
      isVisible: false,
      siteType:[],
      rows:[
        [
          ""
        ]
      ],
      column:[]
    };
  },

  created() {
    this.checkLoggedIn();
  },

  methods: {
    login() {
      this.$router.push({ path: "/login" });
    },
    checkLoggedIn() {
      if (this.$route.params.id) {
        this.isLoggedIn = true;
      }
    },
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/${this.$route.params.role}`,
      });
    },
  },
  computed: {},
};
</script>

<style scoped lang="scss">
.dropdown-wrapper{
  max-width: 350px;
  position: relative;
  margin: 0 auto;

  .selected-item{
    height: 40px;
    border: 2px solid lightgray;
    border-radius: 5px;
    padding: 5px 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 20px;
    font-weight: 500;
  }

  .dropdown-popover{
    position: absolute;
    border: 2px solid lightgray;
    top: 46px;
    left:0;
    right:0;
    background-color: #fff; 
    max-width: 100%;
    padding:10px;

    input{
      width: 100%;
      height: 30px;
      border: 2px solid lightgray;
      font-size: 16px;
      padding-left: 5px;

    }

    .options{
      width: 100%;

      ul{
        list-style: none;
        text-align: left;
        padding-left: 10px;
        max-height: 180px;
        overflow-y: scroll;
        overflow-x: hidden;

      
      li{
        width: 100%;
        border-bottom: 1px solid lightgray;
        padding: 10px;
        background-color: #f1f1f1;
        cursor: pointer;
        font-size: 14px;
        &:hover{
          background: #70878a;
          color: white;
        }
      }
      }



    }
  }
}

.table{
  margin-top:20px;
}


</style>