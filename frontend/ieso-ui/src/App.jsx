import { useState, useEffect } from 'react'
import './App.css'
import axios from 'axios'
import { DemandChart } from "./DemandChart"


const api = axios.create({
  baseURL: `http://localhost:8080/api`
})

function App() {
  const [chartData, setChartData] = useState([])
  let endDt = null;
  const data = [];

  const getDemandData = () => {
    api.get('/data')
    .then( res => {
      // console.log(res.data)
      setChartData(prevData => {
        const newData = res.data
        sessionStorage.setItem('demandData', JSON.stringify(newData))
        endDt = newData[newData.length-1].timestamp;
        return newData
      })
    }).catch( err => {
      console.log(err)
    })
  }

  function getCurrentTime() {
    const now = new Date();
    const offset = now.getTimezoneOffset() * 60000; // Offset in milliseconds
    const nycOffset = -4 * 3600 * 1000; // Offset for New York (EST/EDT) in milliseconds
    const nycDate = new Date(now.getTime() + nycOffset);
    // Convert to ISO string
    return nycDate.toISOString().substring(0,19);
  }

  const getIncrementalDemandData = () => {
    const current = getCurrentTime()
    console.log('/data?startTime=' + endDt + '&endTime=' + current)
    api.get('/data?startTime=' + endDt + '&endTime=' + current)
    .then( res => {
      const newData = res.data
      if (Object.keys(newData).length != 0) {
        endDt = newData[newData.length-1].timestamp
        const prevDataFromSession = JSON.parse(sessionStorage.getItem('demandData'))
        console.log('new data from API: ' + newData)
        const result = [...prevDataFromSession, ...newData]
        sessionStorage.setItem('demandData', result)
        console.log('Length after update: ' + result.length)
        setChartData(result)
      } else {
        console.log("No update")
      }
      
    }).catch( err => {
      console.log(err)
    })
  }

  useEffect(() => {
    getDemandData();

    const incrementInterval = setInterval(getIncrementalDemandData, 300000)
    return () => clearInterval(incrementInterval)
  }, [])

  function updateEndDate(dt) {
    setEndDt(dt)
  }

  return (
    <>
      <div className='chartContainer'>
        <DemandChart demandData={chartData} />
      </div>
    </>
  )
}

export default App
