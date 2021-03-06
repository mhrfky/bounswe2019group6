<template>
  <el-card style="margin-bottom:20px;">
    <div
      slot="header"
      class="clearfix"
    >
      <span>About me</span>
    </div>
    <div class="user-profile">
      <div class="box-center">
        <pan-thumb
          image="https://www.sackettwaconia.com/wp-content/uploads/default-profile.png"
        />
      </div>
      <div class="box-center">
        <div class="user-name text-center">
          {{ user.username }}
        </div>
        <div class="user-name text-center">
          {{ user.email }}
        </div>
        <div style='text-align: center' v-if="!isSelf">
          <el-button 
            @click.native.prevent="takeFollowUnfollowAction()"
            :type="this.isNotFollowing ? 'primary' : this.isFollowing ? 'danger' : 'warning'" 
            plain>{{ this.followText }}
          </el-button>
        </div>
      </div>
    </div>
    <div class="user-bio">
      <div class="user-personal-info user-bio-section">
        <div class="user-bio-section-header">
          <svg-icon icon-class="user" /><span>Personal Info</span>
        </div>
        <div class="user-bio-section-body">
          <div class="user-stats">
            <p><b>Follower:</b> {{ user.followersCount }}</p>
            <p><b>Following:</b> {{ user.followingsCount }}</p>
            <p :style=hidePrivateFields><b>Articles:</b> {{ user.articlesCount }}</p>
            <p :style=hidePrivateFields><b>Comments:</b> {{ user.commentsCount }}</p>
            <p :style=hidePrivateFields><b>Prediction Rate:</b> {{ predictionRate }}%</p>
            <p :style=hidePrivateFields><b>Prediction Num:</b> {{ predictionNumber }}</p>
          </div>
          <div class="user-type">
            <p><b>User Type:</b> {{ user.roles[0] == "ROLE_TRADER" ? "Trader" : "Basic" }}</p>
          </div>
          <a :style=showIban><p :style=hidePrivateFields><b>IBAN:</b> {{ user.iban }}</p></a>
          
          <p :style=hidePrivateFields><b>Latitude:</b> {{ user.latitude }}</p>
          <p :style=hidePrivateFields><b>Longitude:</b> {{ user.longitude }}</p>
          <p><b>Current Status:</b> {{ user.isPrivate ? "Private" : !user.isPrivate ? 'Public': "" }}</p>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
import PanThumb from '@/components/PanThumb'
import { getUser } from '@/api/user'

export default {
  components: { PanThumb },
  props: {
    user: Object
  },
  data() {
    return {
      isFollowing : '',
      isNotFollowing : '',
      isPending : '',
      followText : '',
      isSelf : true,
      hidePrivateFields: 'display: none',
      showIban: 'display: block',
      predictionRate: 0,
      predictionNumber: 0,
    }
  },
  async created() {
    if ((this.$route.path.split('/')[1]) == 'profile') {
      this.isSelf = true
      this.hidePrivateFields = 'display: block'
      this.getMyStats()
    } else {
      this.isSelf = false
      await getUser((this.$route.path.split('/')[2])).then(response => {
        // TODO changing this.user creates a bug in here
        this.user = response.data
        this.hidePrivateFields = this.user.isPrivate ? 'display: none' : 'display: block'
        this.showIban = this.user.iban==null ? 'display: none' : 'display: block'
        this.isFollowing = this.user.followingStatus == 'FOLLOWING' ? true : false
        this.isNotFollowing = this.user.followingStatus == 'NOT_FOLLOWING' ? true : false
        this.isPending = this.user.followingStatus == 'PENDING' ? true : false
        this.followText = this.isNotFollowing ? "Follow" : this.isFollowing ? "Unfollow" : 'Requested'
      })
    }
  },
  methods: {
    getMyStats(){
      this.$store.dispatch('user/getMyStats', this.$store.getters.userInfo.username).then(() => {
        var res = this.$store.getters.myStats
        this.predictionNumber = res.totalPredictions
        this.predictionRate = res.successPercentage
        console.log(res)
      }).catch(err => {
        console.log(err)
      })
    },
    takeFollowUnfollowAction() {
      if(this.isNotFollowing){
        this.$store.dispatch('user/followUser', {'username' : this.user.username}).then(response => {
          this.isNotFollowing = false
          if (this.user.isPrivate){
            this.$message.success('Follow Request Is Sent')
            this.isPending = true
            this.isFollowing = false
          } else {
            this.$message.success('Follow User Success')
            this.isFollowing = true  
            this.isPending = false
          }
          this.followText = this.isNotFollowing ? "Follow" : this.isFollowing ? "Unfollow" : 'Requested'
        }).catch(err => {
          console.log(err)
        })
      } else {
        var tempIsPending = this.isPending
        this.$store.dispatch('user/unfollowUser', {'username' : this.user.username}).then(() => {
          this.isNotFollowing = true
          if (tempIsPending){
            this.$message.success('Follow Request Is Deleted')
            this.isPending = false
          } else {
            this.$message.success('Unfollowed User Success')
            this.isFollowing = false
          }
          this.followText = this.isNotFollowing ? "Follow" : this.isFollowing ? "Unfollow" : 'Requested'
        }).catch(() => {
        })
      }
    },
  },
}
</script>

<style lang="scss" scoped>
 .box-center {
   margin: 0 auto;
   display: table;
 }

 .text-muted {
   color: #777;
 }

 .user-profile {
   .user-name {
     font-weight: bold;
     padding-bottom: 5px;
   }

   .box-center {
     padding-top: 10px;
   }

   .user-role {
     padding-top: 10px;
     font-weight: 400;
     font-size: 14px;
   }

   .box-social {
     padding-top: 30px;

     .el-table {
       border-top: 1px solid #dfe6ec;
     }
   }

   .user-follow {
     padding-top: 20px;
   }
 }

 .user-bio {
   margin-top: 20px;
   color: #606266;

   span {
     padding-left: 4px;
   }

   .user-bio-section {
     font-size: 14px;
     padding: 15px 0;

     .user-bio-section-header {
       border-bottom: 1px solid #dfe6ec;
       padding-bottom: 10px;
       margin-bottom: 10px;
       font-weight: bold;
     }
   }
 }
</style>
